package ru.reformers.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.reformers.model.Reformer;
import ru.reformers.repository.ReformerRepository;

@Service
@Transactional
public class ReformerService {

    private final ReformerRepository reformerRepository;

    private static final List<String> RANK_ORDER = List.of("S", "A", "B", "C", "D", "F");

    public ReformerService(ReformerRepository reformerRepository) {
        this.reformerRepository = reformerRepository;
    }

    public List<Reformer> findAll() {
        return reformerRepository.findAllByOrderByIdAsc();
    }

    public Optional<Reformer> findById(Long id) {
        return reformerRepository.findById(id);
    }

    public List<String> findDistinctEras() {
        return reformerRepository.findDistinctEras();
    }

    public List<Reformer> searchByName(String query) {
        return search(query, null);
    }

    public List<Reformer> search(String query, String era) {
        boolean hasQuery = query != null && !query.isBlank();
        boolean hasEra = era != null && !era.isBlank();

        if (hasQuery && hasEra) {
            return reformerRepository.findByFullNameContainingIgnoreCaseAndEraOrderByIdAsc(
                    query.trim(), era);
        }
        if (hasEra) {
            return reformerRepository.findByEraOrderByIdAsc(era);
        }
        if (hasQuery) {
            return reformerRepository.findByFullNameContainingIgnoreCaseOrderByIdAsc(query.trim());
        }
        return findAll();
    }

    public Map<String, List<Reformer>> buildTierList(String category) {
        Map<String, List<Reformer>> byRank = new HashMap<>();
        for (String rank : RANK_ORDER) {
            byRank.put(rank, new ArrayList<>());
        }

        List<Reformer> all = findAll();
        for (Reformer r : all) {
            String rank = getRatingByCategory(r, category);
            if (!RANK_ORDER.contains(rank)) {
                rank = "F";
            }
            byRank.computeIfAbsent(rank, k -> new ArrayList<>()).add(r);
        }

        // sort each rank by name
        for (String rank : byRank.keySet()) {
            List<Reformer> sorted = byRank.get(rank).stream()
                    .sorted(Comparator.comparing(Reformer::getFullName))
                    .collect(Collectors.toList());
            byRank.put(rank, sorted);
        }

        return byRank;
    }

    public String getRatingByCategory(Reformer reformer, String category) {
        return switch (category) {
            case "economy" -> nullToF(reformer.getEconomyRating());
            case "military" -> nullToF(reformer.getMilitaryRating());
            case "social" -> nullToF(reformer.getSocialRating());
            case "education" -> nullToF(reformer.getEducationRating());
            case "governance" -> nullToF(reformer.getGovernanceRating());
            case "overall" -> nullToF(reformer.getOverallRating());
            default -> nullToF(reformer.getOverallRating());
        };
    }

    public static String categoryLabel(String category) {
        return switch (category) {
            case "economy" -> "Экономика";
            case "military" -> "Военные реформы";
            case "social" -> "Социальные реформы";
            case "education" -> "Образование";
            case "governance" -> "Государственное управление";
            case "overall" -> "Общий рейтинг";
            default -> "Общий рейтинг";
        };
    }

    private String nullToF(String v) {
        return v == null ? "F" : v;
    }

    public int compareRanks(String left, String right) {
        int li = RANK_ORDER.indexOf(nullToF(left));
        int ri = RANK_ORDER.indexOf(nullToF(right));
        return Integer.compare(li, ri); // lower index = better rank
    }

}

