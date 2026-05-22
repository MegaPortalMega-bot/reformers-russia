package ru.reformers.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Эпохи реформаторов (соответствуют каноническому списку проекта).
 */
public final class ReformerEraCatalog {

    private static final Map<String, String> BY_NAME;

    static {
        Map<String, String> m = new LinkedHashMap<>();
        put(m, "Владимир Святой", "X век");
        put(m, "Ярослав Мудрый", "XI век");
        put(m, "Алексей Адашев", "XVI век");
        put(m, "Никон", "XVII век");
        put(m, "Фёдор Апраксин", "XVIII век");
        put(m, "Павел Киселёв", "XIX век");
        put(m, "Николай Милютин", "XIX век");
        put(m, "Дмитрий Милютин", "XIX век");
        put(m, "Иван III Великий", "XV век");
        put(m, "Иван IV Грозный", "XVI век");
        put(m, "Пётр I", "XVIII век");
        put(m, "Екатерина II", "XVIII век");
        put(m, "Михаил Сперанский", "XIX век");
        put(m, "Александр II", "XIX век");
        put(m, "Сергей Витте", "XIX—XX век");
        put(m, "Пётр Столыпин", "Начало XX века");
        put(m, "Алексей Косыгин", "XX век");
        put(m, "Егор Гайдар", "Конец XX века");
        put(m, "Сильвестр", "XVI век");
        put(m, "Борис Годунов", "XVI—XVII век");
        put(m, "Александр Меншиков", "XVIII век");
        put(m, "Виктор Кочубей", "XIX век");
        put(m, "Дмитрий Блудов", "XIX век");
        put(m, "Яков Ростовцев", "XIX век");
        put(m, "Иван Вышнеградский", "XIX век");
        put(m, "Николай Бунге", "XIX век");
        put(m, "Владимир Ленин", "XX век");
        put(m, "Юрий Андропов", "XX век");
        put(m, "Елена Глинская", "XVI век");
        put(m, "Ордин-Нащокин", "XVII век");
        put(m, "Григорий Потёмкин", "XVIII век");
        put(m, "Михаил Ломоносов", "XVIII век");
        put(m, "Николай Новиков", "XVIII век");
        put(m, "Александр Радищев", "XVIII век");
        put(m, "Михаил Лорис-Меликов", "XIX век");
        put(m, "Константин Победоносцев", "XIX век");
        put(m, "Николай Вавилов", "XX век");
        put(m, "Андрей Сахаров", "XX век");
        put(m, "Иван Фёдоров", "XVI век");
        put(m, "Витус Беринг", "XVIII век");
        put(m, "Ефим и Мирон Черепановы", "XIX век");
        put(m, "Сергей Боткин", "XIX век");
        put(m, "Иван Сеченов", "XIX век");
        put(m, "Константин Циолковский", "XIX—XX век");
        put(m, "Владимир Вернадский", "XIX—XX век");
        put(m, "Игорь Курчатов", "XX век");
        put(m, "Михаил Горбачёв", "Конец XX века");
        put(m, "Борис Ельцин", "Конец XX века");
        put(m, "Вячеслав Молотов", "XX век");
        put(m, "Анастас Микоян", "XX век");
        BY_NAME = Collections.unmodifiableMap(m);
    }

    private ReformerEraCatalog() {
    }

    private static void put(Map<String, String> m, String name, String era) {
        m.put(name, era);
    }

    public static Optional<String> find(String fullName) {
        return Optional.ofNullable(BY_NAME.get(fullName));
    }
}
