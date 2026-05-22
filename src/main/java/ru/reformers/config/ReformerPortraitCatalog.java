package ru.reformers.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Каталог URL портретов реформаторов (Wikimedia Commons / Википедия).
 * Для записей без открытого портрета используется плейсхолдер — см. комментарии PLACEHOLDER.
 */
public final class ReformerPortraitCatalog {

    private static final Map<String, String> PORTRAITS;
    private static final Map<String, String> WIKI_TITLES;

    static {
        Map<String, String> portraits = new HashMap<>();
        Map<String, String> wiki = new HashMap<>();

        // --- Реальные портреты (Wikimedia Commons) ---
        put(portraits, wiki, "Иван III Великий", "Иван III",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Ivan_III_of_Russia_3.jpg/330px-Ivan_III_of_Russia_3.jpg");
        put(portraits, wiki, "Иван IV Грозный", "Иван IV",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/Ivan_grozny_frame.jpg/330px-Ivan_grozny_frame.jpg");
        put(portraits, wiki, "Пётр I", "Пётр I",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Inconnu_d%27apr%C3%A8s_J.-M._Nattier%2C_Portrait_de_Pierre_Ier_%28mus%C3%A9e_de_l%E2%80%99Ermitage%29.jpg/330px-Inconnu_d%27apr%C3%A8s_J.-M._Nattier%2C_Portrait_de_Pierre_Ier_%28mus%C3%A9e_de_l%E2%80%99Ermitage%29.jpg");
        put(portraits, wiki, "Екатерина II", "Екатерина II",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Catherine_II_after_Roslin%2C_Rokotov_%281780s%2C_Kunsthistorisches_Museum%29.jpg/330px-Catherine_II_after_Roslin%2C_Rokotov_%281780s%2C_Kunsthistorisches_Museum%29.jpg");
        put(portraits, wiki, "Михаил Сперанский", "Сперанский, Михаил Михайлович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/%D0%9F%D0%BE%D1%80%D1%82%D1%80%D0%B5%D1%82_%D0%A1%D0%BF%D0%B5%D1%80%D0%B0%D0%BD%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%9C%D0%B8%D1%85%D0%B0%D0%B8%D0%BB%D0%B0_%D0%9C%D0%B8%D1%85%D0%B0%D0%B9%D0%BB%D0%BE%D0%B2%D0%B8%D1%87%D0%B0.jpg/330px-%D0%9F%D0%BE%D1%80%D1%82%D1%80%D0%B5%D1%82_%D0%A1%D0%BF%D0%B5%D1%80%D0%B0%D0%BD%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%9C%D0%B8%D1%85%D0%B0%D0%B8%D0%BB%D0%B0_%D0%9C%D0%B8%D1%85%D0%B0%D0%B9%D0%BB%D0%BE%D0%B2%D0%B8%D1%87%D0%B0.jpg");
        put(portraits, wiki, "Александр II", "Александр II",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Alexander_II_of_Russia_photo.jpg/330px-Alexander_II_of_Russia_photo.jpg");
        put(portraits, wiki, "Сергей Витте", "Витте, Сергей Юльевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/SergeiWitte01548v.jpg/330px-SergeiWitte01548v.jpg");
        put(portraits, wiki, "Пётр Столыпин", "Столыпин, Пётр Аркадьевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Pyotr_Stolypin_LOC_07327.jpg/330px-Pyotr_Stolypin_LOC_07327.jpg");
        put(portraits, wiki, "Алексей Косыгин", "Косыгин, Алексей Николаевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/1971_Wire_Photo_press_conference_in_Ottawa_Canada_Soviet_Premier_Alexei_Kosygin.jpg/330px-1971_Wire_Photo_press_conference_in_Ottawa_Canada_Soviet_Premier_Alexei_Kosygin.jpg");
        put(portraits, wiki, "Егор Гайдар", "Гайдар, Егор Тимурович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Ba-gaidar-e-t-1999-square_%28cropped%29.jpg/330px-Ba-gaidar-e-t-1999-square_%28cropped%29.jpg");
        put(portraits, wiki, "Владимир Святой", "Владимир Святославич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/%D0%9F%D0%BE%D1%85%D0%BE%D0%B4_%D0%92%D0%BB%D0%B0%D0%B4%D0%B8%D0%BC%D0%B8%D1%80%D0%B0_I_%D0%BD%D0%B0_%D0%BF%D0%B5%D1%87%D0%B5%D0%BD%D0%B5%D0%B3%D0%BE%D0%B2.jpg/330px-%D0%9F%D0%BE%D1%85%D0%BE%D0%B4_%D0%92%D0%BB%D0%B0%D0%B4%D0%B8%D0%BC%D0%B8%D1%80%D0%B0_I_%D0%BD%D0%B0_%D0%BF%D0%B5%D1%87%D0%B5%D0%BD%D0%B5%D0%B3%D0%BE%D0%B2.jpg");
        put(portraits, wiki, "Ярослав Мудрый", "Ярослав Мудрый",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/%D0%AF%D1%80%D0%BE%D1%81%D0%BB%D0%B0%D0%B2_%D0%9C%D1%83%D0%B4%D1%80%D1%8B%D0%B9.jpg/330px-%D0%AF%D1%80%D0%BE%D1%81%D0%BB%D0%B0%D0%B2_%D0%9C%D1%83%D0%B4%D1%80%D1%8B%D0%B9.jpg");
        put(portraits, wiki, "Алексей Адашев", "Адашев, Алексей Фёдорович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/1000_Adashev.jpg/330px-1000_Adashev.jpg");
        put(portraits, wiki, "Никон", "Никон (патриарх)",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Portrait_of_Patriarch_Nikon.jpg/330px-Portrait_of_Patriarch_Nikon.jpg");
        put(portraits, wiki, "Фёдор Апраксин", "Апраксин, Фёдор Матвеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Portrait_of_Count_Fyodor_Matveyevich_Apraksin.jpg/330px-Portrait_of_Count_Fyodor_Matveyevich_Apraksin.jpg");
        put(portraits, wiki, "Павел Киселёв", "Киселёв, Павел Дмитриевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Kiseleff.jpg/330px-Kiseleff.jpg");
        put(portraits, wiki, "Николай Милютин", "Милютин, Николай Алексеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Milutin_Nikolay_Alexeevich.jpg/330px-Milutin_Nikolay_Alexeevich.jpg");
        put(portraits, wiki, "Дмитрий Милютин", "Милютин, Дмитрий Алексеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/%D0%9C%D0%B8%D0%BB%D1%8E%D1%82%D0%B8%D0%BD%2C_%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B9_%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B5%D0%B5%D0%B2%D0%B8%D1%87.jpg/330px-%D0%9C%D0%B8%D0%BB%D1%8E%D1%82%D0%B8%D0%BD%2C_%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B9_%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B5%D0%B5%D0%B2%D0%B8%D1%87.jpg");
        put(portraits, wiki, "Вячеслав Молотов", "Молотов, Вячеслав Михайлович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/V.M._Molotov_TASS_Portrait_Trim_Edit.jpg/330px-V.M._Molotov_TASS_Portrait_Trim_Edit.jpg");
        put(portraits, wiki, "Анастас Микоян", "Микоян, Анастас Иванович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/A.I._Mikoyan_TASS_Portrait_Trim_Edit.jpg/330px-A.I._Mikoyan_TASS_Portrait_Trim_Edit.jpg");
        put(portraits, wiki, "Елена Глинская", "Елена Глинская",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Glinskaya_reconstruction_02.JPG/330px-Glinskaya_reconstruction_02.JPG");
        put(portraits, wiki, "Ордин-Нащокин", "Ордин-Нащокин, Афанасий Лаврентьевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/%D0%9E%D1%80%D0%B4%D0%B8%D0%BD-%D0%9D%D0%B0%D1%89%D0%BE%D0%BA%D0%B8%D0%BD.jpg/330px-%D0%9E%D1%80%D0%B4%D0%B8%D0%BD-%D0%9D%D0%B0%D1%89%D0%BE%D0%BA%D0%B8%D0%BD.jpg");
        put(portraits, wiki, "Григорий Потёмкин", "Потёмкин, Григорий Александрович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Princepotemkin.jpg/330px-Princepotemkin.jpg");
        put(portraits, wiki, "Михаил Ломоносов", "Ломоносов, Михаил Васильевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Mikhail_Lomonosov_%281757%29.jpg/330px-Mikhail_Lomonosov_%281757%29.jpg");
        put(portraits, wiki, "Николай Новиков", "Новиков, Николай Иванович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/NI_Novikov.jpg/330px-NI_Novikov.jpg");
        put(portraits, wiki, "Александр Радищев", "Радищев, Александр Николаевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Radishchev_color.jpg/330px-Radishchev_color.jpg");
        put(portraits, wiki, "Михаил Лорис-Меликов", "Лорис-Меликов, Михаил Тариелович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/LorisMelikov_Aivazovsky.jpg/330px-LorisMelikov_Aivazovsky.jpg");
        put(portraits, wiki, "Константин Победоносцев", "Победоносцев, Константин Петрович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/%D0%9F%D0%BE%D1%80%D1%82%D1%80%D0%B5%D1%82_%D0%9A%D0%BE%D0%BD%D1%81%D1%82%D0%B0%D0%BD%D1%82%D0%B8%D0%BD%D0%B0_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2%D0%B8%D1%87%D0%B0_%D0%9F%D0%BE%D0%B1%D0%B5%D0%B4%D0%BE%D0%BD%D0%BE%D1%81%D1%86%D0%B5%D0%B2%D0%B0._Denier%2C_~1890%281896%29%D0%B3_%D0%93%D0%98%D0%9C_e1t3.jpg/330px-%D0%9F%D0%BE%D1%80%D1%82%D1%80%D0%B5%D1%82_%D0%9A%D0%BE%D0%BD%D1%81%D1%82%D0%B0%D0%BD%D1%82%D0%B8%D0%BD%D0%B0_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2%D0%B8%D1%87%D0%B0_%D0%9F%D0%BE%D0%B1%D0%B5%D0%B4%D0%BE%D0%BD%D0%BE%D1%81%D1%86%D0%B5%D0%B2%D0%B0._Denier%2C_~1890%281896%29%D0%B3_%D0%93%D0%98%D0%9C_e1t3.jpg");
        put(portraits, wiki, "Николай Вавилов", "Вавилов, Николай Иванович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Nikolai_Vavilov_NYWTS.jpg/330px-Nikolai_Vavilov_NYWTS.jpg");
        put(portraits, wiki, "Андрей Сахаров", "Сахаров, Андрей Дмитриевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/84/RIAN_archive_25981_Academician_Sakharov.jpg/330px-RIAN_archive_25981_Academician_Sakharov.jpg");
        put(portraits, wiki, "Иван Фёдоров", "Иван Фёдоров (первопечатник)",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Portrait_of_Ivan_Fedorov_by_I._Tomaszewicz.jpg/330px-Portrait_of_Ivan_Fedorov_by_I._Tomaszewicz.jpg");
        put(portraits, wiki, "Витус Беринг", "Беринг, Витус",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/%D0%92%D0%B8%D1%82%D1%83%D1%81%D0%98%D0%BE%D0%BD%D0%B0%D1%81%D1%81%D0%B5%D0%BD%D0%91%D0%B5%D1%80%D0%B8%D0%BD%D0%B3.jpg/330px-%D0%92%D0%B8%D1%82%D1%83%D1%81%D0%98%D0%BE%D0%BD%D0%B0%D1%81%D1%81%D0%B5%D0%BD%D0%91%D0%B5%D1%80%D0%B8%D0%BD%D0%B3.jpg");
        put(portraits, wiki, "Ефим и Мирон Черепановы", "Черепанов, Ефим Алексеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Efim_Alekseevich_Cherepanov_%281774-1842%29.jpg/330px-Efim_Alekseevich_Cherepanov_%281774-1842%29.jpg");
        put(portraits, wiki, "Сергей Боткин", "Боткин, Сергей Петрович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Sergey_Botkin_after_Kramskoi.jpg/330px-Sergey_Botkin_after_Kramskoi.jpg");
        put(portraits, wiki, "Иван Сеченов", "Сеченов",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Repin_SechenovIM.jpg/330px-Repin_SechenovIM.jpg");
        put(portraits, wiki, "Константин Циолковский", "Циолковский, Константин Эдуардович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/%D0%9A%D0%BE%D0%BD%D1%81%D1%82%D0%B0%D0%BD%D1%82%D0%B8%D0%BD_%D0%A6%D0%B8%D0%BE%D0%BB%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%B8%D0%B9.jpg/330px-%D0%9A%D0%BE%D0%BD%D1%81%D1%82%D0%B0%D0%BD%D1%82%D0%B8%D0%BD_%D0%A6%D0%B8%D0%BE%D0%BB%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%B8%D0%B9.jpg");
        put(portraits, wiki, "Владимир Вернадский", "Вернадский, Владимир Иванович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/1889-VernadskyVI-Paris.jpg/330px-1889-VernadskyVI-Paris.jpg");
        put(portraits, wiki, "Игорь Курчатов", "Курчатов, Игорь Васильевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Igor_Kurchatov_001.png/330px-Igor_Kurchatov_001.png");
        put(portraits, wiki, "Михаил Горбачёв", "Горбачёв, Михаил Сергеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/GorbachevMS.jpg/330px-GorbachevMS.jpg");
        put(portraits, wiki, "Борис Ельцин", "Ельцин, Борис Николаевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/%D0%91%D0%BE%D1%80%D0%B8%D1%81_%D0%9D%D0%B8%D0%BA%D0%BE%D0%BB%D0%B0%D0%B5%D0%B2%D0%B8%D1%87_%D0%95%D0%BB%D1%8C%D1%86%D0%B8%D0%BD-1_%28cropped%29_%28cropped%29.jpg/330px-%D0%91%D0%BE%D1%80%D0%B8%D1%81_%D0%9D%D0%B8%D0%BA%D0%BE%D0%BB%D0%B0%D0%B5%D0%B2%D0%B8%D1%87_%D0%95%D0%BB%D1%8C%D1%86%D0%B8%D0%BD-1_%28cropped%29_%28cropped%29.jpg");
        // Портрет дьяка Сильвестра (Wikimedia Commons: 1000_Silvestr.jpg)
        put(portraits, wiki, "Сильвестр", "Сильвестр (дьяк)",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/1000_Silvestr.jpg/500px-1000_Silvestr.jpg");
        put(portraits, wiki, "Борис Годунов", "Борис Годунов",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Portrait_of_Boris_Godunov.jpg/330px-Portrait_of_Boris_Godunov.jpg");
        put(portraits, wiki, "Александр Меншиков", "Меншиков, Александр Данилович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Portrait_of_Alexander_Danilovich_Menshikov1.jpg/330px-Portrait_of_Alexander_Danilovich_Menshikov1.jpg");
        put(portraits, wiki, "Виктор Кочубей", "Кочубей, Виктор Павлович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Kochubey_vp.jpg/330px-Kochubey_vp.jpg");
        put(portraits, wiki, "Дмитрий Блудов", "Блудов, Дмитрий Николаевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/PGRS_1_013_Bludov_-_crop.jpg/330px-PGRS_1_013_Bludov_-_crop.jpg");
        put(portraits, wiki, "Яков Ростовцев", "Ростовцев, Яков Иванович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Sergey_Zaryanko_21.jpg/330px-Sergey_Zaryanko_21.jpg");
        put(portraits, wiki, "Иван Вышнеградский", "Вышнеградский, Иван Алексеевич",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Vyshnegradsky_Ivan_%281831-1895%29.jpg/330px-Vyshnegradsky_Ivan_%281831-1895%29.jpg");
        put(portraits, wiki, "Николай Бунге", "Бунге, Николай Христианович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Ivan_Tyurin_-_Portrait_of_N.H.Bunge%2C_1887.jpg/330px-Ivan_Tyurin_-_Portrait_of_N.H.Bunge%2C_1887.jpg");
        put(portraits, wiki, "Владимир Ленин", "Ленин",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Vladimir_Lenin_in_July_1920_by_Pavel_Zhukov.jpg/330px-Vladimir_Lenin_in_July_1920_by_Pavel_Zhukov.jpg");
        put(portraits, wiki, "Юрий Андропов", "Андропов, Юрий Владимирович",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Yuri_Andropov.jpg/330px-Yuri_Andropov.jpg");

        PORTRAITS = Collections.unmodifiableMap(portraits);
        WIKI_TITLES = Collections.unmodifiableMap(wiki);
    }

    private ReformerPortraitCatalog() {
    }

    private static void put(Map<String, String> portraits, Map<String, String> wiki,
                            String displayName, String wikiTitle, String imageUrl) {
        portraits.put(displayName, compactWikimediaUrl(imageUrl));
        wiki.put(displayName, wikiTitle);
    }

    /**
     * Укорачивает URL Wikimedia: для длинных thumb-ссылок сохраняем прямой путь к файлу (&lt;= 512 символов).
     */
    public static String compactWikimediaUrl(String url) {
        if (url == null || url.length() <= 512) {
            return url;
        }
        int thumbIdx = url.indexOf("/thumb/");
        if (thumbIdx > 0) {
            String afterThumb = url.substring(thumbIdx + "/thumb/".length());
            int lastSlash = afterThumb.lastIndexOf('/');
            if (lastSlash > 0) {
                String pathPart = afterThumb.substring(0, lastSlash);
                return "https://upload.wikimedia.org/wikipedia/commons/" + pathPart;
            }
        }
        return url;
    }

    /** URL портрета из каталога или плейсхолдер, если имя неизвестно. */
    public static String resolveImageUrl(String fullName) {
        return PORTRAITS.getOrDefault(fullName, placeholder(fullName));
    }

    public static Optional<String> getWikiTitle(String fullName) {
        return Optional.ofNullable(WIKI_TITLES.get(fullName));
    }

    /** URL статьи на русской Википедии по заголовку. */
    public static String wikipediaUrl(String wikiTitle) {
        return "https://ru.wikipedia.org/wiki/" + wikiTitle.replace(' ', '_');
    }

    public static boolean isPlaceholder(String imageUrl) {
        return imageUrl == null
                || imageUrl.isBlank()
                || imageUrl.contains("via.placeholder.com");
    }

    public static boolean hasRealPortrait(String fullName) {
        String url = PORTRAITS.get(fullName);
        return url != null && !isPlaceholder(url);
    }

    public static Map<String, String> allPortraits() {
        return PORTRAITS;
    }

    public static String placeholder(String fullName) {
        return "https://via.placeholder.com/300x400?text="
                + URLEncoder.encode(fullName, StandardCharsets.UTF_8);
    }
}
