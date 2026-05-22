package ru.reformers.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.reformers.model.Reformer;
import ru.reformers.repository.ReformerRepository;
import ru.reformers.service.WikiEnrichmentService;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ReformerRepository repository, WikiEnrichmentService wikiEnrichmentService) {
        return args -> {
            if (needsReseed(repository)) {
                repository.deleteAll();
            }

            if (repository.count() == 0) {
                List<Reformer> reformers = new ArrayList<>();
                for (String fullName : ReformerRatingsCatalog.orderedNames()) {
                    String era = ReformerEraCatalog.find(fullName)
                            .orElse("Россия");
                    Reformer r = seed(fullName, era);
                    wikiEnrichmentService.enrichIfMissing(r);
                    reformers.add(r);
                }
                repository.saveAll(reformers);
                return;
            }

            repository.findAll().forEach(reformer -> {
                applyPortraitFromCatalog(reformer, repository);
                applyTextsFromCatalog(reformer, repository);
                applyRatingsFromCatalog(reformer, repository);
            });
        };
    }

    /** id=1 — Владимир Святой, id=11 — Пётр I (канонический порядок списка). */
    private boolean needsReseed(ReformerRepository repository) {
        if (repository.count() != ReformerRatingsCatalog.CANONICAL_COUNT) {
            return true;
        }
        Optional<Reformer> first = repository.findById(1L);
        if (first.isEmpty() || !"Владимир Святой".equals(first.get().getFullName())) {
            return true;
        }
        Optional<Reformer> eleventh = repository.findById(11L);
        return eleventh.isEmpty() || !"Пётр I".equals(eleventh.get().getFullName());
    }

    private void applyRatingsFromCatalog(Reformer reformer, ReformerRepository repository) {
        ReformerRatingsCatalog.applyTo(reformer);
        repository.save(reformer);
    }

    private void applyTextsFromCatalog(Reformer reformer, ReformerRepository repository) {
        Optional<ReformerContentCatalog.ReformerTextContent> opt = ReformerContentCatalog.find(reformer.getFullName());
        if (opt.isEmpty()) {
            return;
        }
        ReformerContentCatalog.ReformerTextContent c = opt.get();
        reformer.setShortBio(c.shortBio());
        reformer.setDetailedBio(c.detailedBio());
        reformer.setReforms(c.reforms());
        reformer.setKeyAchievements(c.keyAchievements());
        if (c.birthYear() != null) {
            reformer.setBirthYear(c.birthYear());
        }
        if (c.deathYear() != null) {
            reformer.setDeathYear(c.deathYear());
        }
        repository.save(reformer);
    }

    private void applyPortraitFromCatalog(Reformer reformer, ReformerRepository repository) {
        if (!ReformerPortraitCatalog.isPlaceholder(reformer.getImageUrl())) {
            return;
        }
        String catalogUrl = ReformerPortraitCatalog.resolveImageUrl(reformer.getFullName());
        if (!catalogUrl.equals(reformer.getImageUrl())) {
            reformer.setImageUrl(catalogUrl);
            repository.save(reformer);
        }
    }

    private Reformer seed(String fullName, String era) {
        Optional<ReformerContentCatalog.ReformerTextContent> tc = ReformerContentCatalog.find(fullName);
        String shortBio = tc.map(ReformerContentCatalog.ReformerTextContent::shortBio)
                .orElse("Историческая личность, " + era);
        String detailedBio = tc.map(ReformerContentCatalog.ReformerTextContent::detailedBio)
                .orElse("Эта карточка описывает реформатора " + fullName + " (эпоха: " + era + ").");
        String reforms = tc.map(ReformerContentCatalog.ReformerTextContent::reforms)
                .orElse("—");
        String achievements = tc.map(ReformerContentCatalog.ReformerTextContent::keyAchievements)
                .orElse("—");
        Integer birthYear = tc.map(ReformerContentCatalog.ReformerTextContent::birthYear).orElse(null);
        Integer deathYear = tc.map(ReformerContentCatalog.ReformerTextContent::deathYear).orElse(null);

        Reformer r = new Reformer();
        r.setFullName(fullName);
        r.setShortBio(shortBio);
        r.setDetailedBio(detailedBio);
        r.setImageUrl(ReformerPortraitCatalog.resolveImageUrl(fullName));
        r.setBirthYear(birthYear);
        r.setDeathYear(deathYear);
        r.setEra(era);
        r.setReforms(reforms);
        r.setKeyAchievements(achievements);
        ReformerRatingsCatalog.applyTo(r);
        return r;
    }
}
