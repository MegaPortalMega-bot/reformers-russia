package ru.reformers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import ru.reformers.model.Reformer;
import ru.reformers.service.ReformerService;
import ru.reformers.util.RatingCalculator;

@Controller
public class ReformerController {

    private final ReformerService reformerService;

    public ReformerController(ReformerService reformerService) {
        this.reformerService = reformerService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "era", required = false) String era,
            Model model) {

        String selectedEra = era != null && !era.isBlank() ? era : "";
        // Полный список для карточек; фильтр по эпохе — на клиенте (search.js)
        List<Reformer> reformers = reformerService.search(query, null);

        model.addAttribute("reformers", reformers);
        model.addAttribute("eras", reformerService.findDistinctEras());
        model.addAttribute("query", query == null ? "" : query);
        model.addAttribute("selectedEra", selectedEra);
        return "index";
    }

    @GetMapping("/reformer/{id}")
    public String reformerDetail(@PathVariable("id") Long id, Model model) {
        Reformer reformer = reformerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("reformer", reformer);
        model.addAttribute("radarScores", String.join(",",
                String.valueOf(RatingCalculator.score(reformer.getEconomyRating())),
                String.valueOf(RatingCalculator.score(reformer.getMilitaryRating())),
                String.valueOf(RatingCalculator.score(reformer.getSocialRating())),
                String.valueOf(RatingCalculator.score(reformer.getEducationRating())),
                String.valueOf(RatingCalculator.score(reformer.getGovernanceRating()))));
        return "reformer-detail";
    }

    @GetMapping("/tierlist")
    public String tierList(
            @RequestParam(name = "category", defaultValue = "economy") String category,
            Model model) {

        Map<String, List<Reformer>> tiers = reformerService.buildTierList(category);
        model.addAttribute("tiers", tiers);
        model.addAttribute("category", category);
        model.addAttribute("categoryLabel", ReformerService.categoryLabel(category));
        model.addAttribute("ranks", List.of("S", "A", "B", "C", "D", "F"));
        return "tierlist";
    }

    @GetMapping("/compare")
    public String compare(
            @RequestParam(name = "id1", required = false) Long id1,
            @RequestParam(name = "id2", required = false) Long id2,
            Model model) {

        Reformer left = null;
        Reformer right = null;

        if (id1 != null) {
            left = reformerService.findById(id1)
                    .orElse(null);
        }
        if (id2 != null) {
            right = reformerService.findById(id2)
                    .orElse(null);
        }

        model.addAttribute("left", left);
        model.addAttribute("right", right);
        model.addAttribute("hasBoth", left != null && right != null);

        if (left != null && right != null) {
            Map<String, Integer> comparison = Map.of(
                    "economy", reformerService.compareRanks(left.getEconomyRating(), right.getEconomyRating()),
                    "military", reformerService.compareRanks(left.getMilitaryRating(), right.getMilitaryRating()),
                    "social", reformerService.compareRanks(left.getSocialRating(), right.getSocialRating()),
                    "education", reformerService.compareRanks(left.getEducationRating(), right.getEducationRating()),
                    "governance", reformerService.compareRanks(left.getGovernanceRating(), right.getGovernanceRating()));

            long leftWins = comparison.values().stream().filter(v -> v < 0).count();
            long rightWins = comparison.values().stream().filter(v -> v > 0).count();

            String summary;
            if (leftWins > rightWins) {
                summary = "По большинству параметров выигрывает: " + left.getFullName();
            } else if (rightWins > leftWins) {
                summary = "По большинству параметров выигрывает: " + right.getFullName();
            } else {
                summary = "По результатам сравнения явного победителя нет.";
            }

            model.addAttribute("comparison", comparison);
            model.addAttribute("leftWins", leftWins);
            model.addAttribute("rightWins", rightWins);
            model.addAttribute("summary", summary);
        }

        // for dropdowns
        model.addAttribute("allReformers", reformerService.findAll());

        return "compare";
    }
}

