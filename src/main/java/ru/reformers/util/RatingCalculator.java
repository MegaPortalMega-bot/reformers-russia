package ru.reformers.util;

import java.util.Map;

/**
 * Подсчёт итогового рейтинга по пяти сферам (S=5 … F=0).
 */
public final class RatingCalculator {

    private static final Map<String, Integer> SCORES = Map.of(
            "S", 5, "A", 4, "B", 3, "C", 2, "D", 1, "F", 0);

    private RatingCalculator() {
    }

    public static int score(String rank) {
        if (rank == null || rank.isBlank()) {
            return 0;
        }
        return SCORES.getOrDefault(rank.trim().toUpperCase(), 0);
    }

    public static String overallFromScores(String economy, String military, String social,
                                           String education, String governance) {
        int sum = score(economy) + score(military) + score(social) + score(education) + score(governance);
        if (sum >= 23) {
            return "S";
        }
        if (sum >= 19) {
            return "A";
        }
        if (sum >= 15) {
            return "B";
        }
        if (sum >= 11) {
            return "C";
        }
        if (sum >= 6) {
            return "D";
        }
        return "F";
    }
}
