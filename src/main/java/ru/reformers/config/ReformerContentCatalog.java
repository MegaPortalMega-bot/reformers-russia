package ru.reformers.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Тексты биографий, реформ и достижений (по открытым историческим источникам, в т.ч. Википедия).
 * Данные загружаются из classpath: /catalog/reformers-chunk-01.json … reformers-chunk-10.json
 */
public final class ReformerContentCatalog {

    private static final Map<String, ReformerTextContent> BY_NAME = loadAll();

    private ReformerContentCatalog() {
    }

    public static Optional<ReformerTextContent> find(String fullName) {
        return Optional.ofNullable(BY_NAME.get(fullName));
    }

    public static Map<String, ReformerTextContent> all() {
        return BY_NAME;
    }

    /** Признак «заглушки» из старого DataLoader — такие записи перезаписываем из каталога. */
    public static boolean looksLikeStubText(String detailedBio, String reforms, String keyAchievements) {
        if (detailedBio == null || detailedBio.isBlank()) {
            return true;
        }
        if (reforms == null || reforms.isBlank()) {
            return true;
        }
        if (keyAchievements == null || keyAchievements.isBlank()) {
            return true;
        }
        String d = detailedBio;
        if (d.contains("демоданными") || d.contains("Эта карточка описывает реформатора")) {
            return true;
        }
        if (reforms.contains("Ключевые реформы и инициативы, связанные с именем")) {
            return true;
        }
        if (keyAchievements.contains("Краткое описание вклада")) {
            return true;
        }
        // Очень короткая «биография» из REST Википедии (один абзац) — заменяем развёрнутым текстом из каталога
        if (d.length() < 600 && !d.contains("\n\n")) {
            return true;
        }
        return false;
    }

    private static Map<String, ReformerTextContent> loadAll() {
        ObjectMapper om = new ObjectMapper();
        Map<String, ReformerTextContent> map = new LinkedHashMap<>();
        List<String> resources = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> String.format("/catalog/reformers-chunk-%02d.json", i))
                .toList();
        for (String path : resources) {
            try (InputStream in = ReformerContentCatalog.class.getResourceAsStream(path)) {
                if (in == null) {
                    continue;
                }
                List<ReformerJsonDto> rows = om.readValue(in, new TypeReference<>() {
                });
                for (ReformerJsonDto dto : rows) {
                    map.put(dto.fullName, new ReformerTextContent(
                            dto.shortBio,
                            dto.detailedBio,
                            dto.reforms,
                            dto.keyAchievements,
                            dto.birthYear,
                            dto.deathYear));
                }
            } catch (IOException e) {
                throw new IllegalStateException("Не удалось загрузить " + path, e);
            }
        }
        return Collections.unmodifiableMap(map);
    }

    /** DTO для Jackson (имена полей = JSON). */
    @SuppressWarnings("unused")
    private static final class ReformerJsonDto {
        public String fullName;
        public Integer birthYear;
        public Integer deathYear;
        public String shortBio;
        public String detailedBio;
        public String reforms;
        public String keyAchievements;
    }

    public record ReformerTextContent(
            String shortBio,
            String detailedBio,
            String reforms,
            String keyAchievements,
            Integer birthYear,
            Integer deathYear) {
    }
}
