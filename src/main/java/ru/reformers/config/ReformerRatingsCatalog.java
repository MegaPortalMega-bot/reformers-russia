package ru.reformers.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.reformers.model.Reformer;
import ru.reformers.util.RatingCalculator;

/**
 * Каталог оценок реформаторов по пяти сферам (источник: редакционная таблица проекта).
 */
public final class ReformerRatingsCatalog {

    public record SphereRatings(
            String economy,
            String military,
            String social,
            String education,
            String governance) {

        public String overall() {
            return RatingCalculator.overallFromScores(economy, military, social, education, governance);
        }
    }

    private static final Map<String, SphereRatings> BY_NAME;

    static {
        Map<String, SphereRatings> m = new LinkedHashMap<>();
        put(m, "Владимир Святой", "D", "B", "S", "A", "A");
        put(m, "Ярослав Мудрый", "B", "B", "A", "S", "S");
        put(m, "Алексей Адашев", "B", "B", "A", "F", "A");
        put(m, "Никон", "F", "F", "C", "B", "B");
        put(m, "Фёдор Апраксин", "D", "S", "F", "B", "B");
        put(m, "Павел Киселёв", "B", "F", "S", "A", "A");
        put(m, "Николай Милютин", "A", "F", "S", "F", "A");
        put(m, "Дмитрий Милютин", "B", "S", "A", "A", "A");
        put(m, "Иван III Великий", "A", "A", "B", "F", "S");
        put(m, "Иван IV Грозный", "B", "A", "B", "B", "A");
        put(m, "Пётр I", "S", "S", "A", "S", "S");
        put(m, "Екатерина II", "A", "B", "B", "A", "S");
        put(m, "Михаил Сперанский", "B", "C", "B", "B", "S");
        put(m, "Александр II", "A", "A", "S", "A", "A");
        put(m, "Сергей Витте", "S", "D", "C", "B", "A");
        put(m, "Пётр Столыпин", "S", "F", "A", "B", "A");
        put(m, "Алексей Косыгин", "A", "C", "B", "F", "B");
        put(m, "Егор Гайдар", "A", "C", "D", "C", "B");
        put(m, "Сильвестр", "C", "C", "B", "B", "B");
        put(m, "Борис Годунов", "B", "B", "B", "B", "A");
        put(m, "Александр Меншиков", "C", "A", "F", "F", "A");
        put(m, "Виктор Кочубей", "F", "F", "F", "B", "A");
        put(m, "Дмитрий Блудов", "C", "C", "B", "C", "A");
        put(m, "Яков Ростовцев", "B", "B", "S", "F", "A");
        put(m, "Иван Вышнеградский", "A", "C", "D", "C", "B");
        put(m, "Николай Бунге", "A", "C", "A", "C", "B");
        put(m, "Владимир Ленин", "A", "B", "S", "A", "S");
        put(m, "Юрий Андропов", "B", "C", "B", "F", "B");
        put(m, "Елена Глинская", "S", "C", "B", "C", "B");
        put(m, "Ордин-Нащокин", "A", "C", "B", "C", "B");
        put(m, "Григорий Потёмкин", "A", "S", "B", "D", "A");
        put(m, "Михаил Ломоносов", "B", "C", "D", "S", "F");
        put(m, "Николай Новиков", "C", "F", "A", "A", "C");
        put(m, "Александр Радищев", "C", "F", "A", "B", "F");
        put(m, "Михаил Лорис-Меликов", "B", "B", "A", "B", "A");
        put(m, "Константин Победоносцев", "C", "C", "D", "B", "B");
        put(m, "Николай Вавилов", "B", "C", "B", "A", "C");
        put(m, "Андрей Сахаров", "F", "A", "S", "A", "B");
        put(m, "Иван Фёдоров", "B", "F", "A", "S", "C");
        put(m, "Витус Беринг", "B", "B", "C", "A", "B");
        put(m, "Ефим и Мирон Черепановы", "B", "C", "D", "B", "C");
        put(m, "Сергей Боткин", "F", "B", "A", "A", "C");
        put(m, "Иван Сеченов", "C", "C", "D", "S", "C");
        put(m, "Константин Циолковский", "C", "B", "C", "A", "C");
        put(m, "Владимир Вернадский", "D", "C", "B", "A", "C");
        put(m, "Игорь Курчатов", "B", "S", "C", "A", "B");
        put(m, "Михаил Горбачёв", "C", "A", "S", "B", "A");
        put(m, "Борис Ельцин", "A", "C", "B", "C", "A");
        put(m, "Вячеслав Молотов", "A", "B", "C", "B", "B");
        put(m, "Анастас Микоян", "A", "C", "B", "C", "B");
        BY_NAME = Collections.unmodifiableMap(m);
    }

    private ReformerRatingsCatalog() {
    }

    private static void put(Map<String, SphereRatings> m, String name,
                            String e, String mil, String soc, String edu, String gov) {
        m.put(name, new SphereRatings(e, mil, soc, edu, gov));
    }

    public static final int CANONICAL_COUNT = 50;

    /** Имена в каноническом порядке (id 1…50 в БД). */
    public static List<String> orderedNames() {
        return List.copyOf(BY_NAME.keySet());
    }

    public static Optional<SphereRatings> find(String fullName) {
        return Optional.ofNullable(BY_NAME.get(fullName));
    }

    public static void applyTo(Reformer reformer) {
        find(reformer.getFullName()).ifPresent(r -> {
            reformer.setEconomyRating(r.economy());
            reformer.setMilitaryRating(r.military());
            reformer.setSocialRating(r.social());
            reformer.setEducationRating(r.education());
            reformer.setGovernanceRating(r.governance());
            reformer.setOverallRating(r.overall());
        });
        ReformerPortraitCatalog.getWikiTitle(reformer.getFullName())
                .ifPresent(title -> reformer.setWikipediaUrl(ReformerPortraitCatalog.wikipediaUrl(title)));
    }
}
