package ru.reformers.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ru.reformers.config.ReformerPortraitCatalog;
import ru.reformers.model.Reformer;

@Service
public class WikiEnrichmentService {

    private final RestClient restClient;

    public WikiEnrichmentService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://ru.wikipedia.org/api/rest_v1")
                .build();
    }

    public Optional<WikiSummaryResponse> fetchSummary(String title) {
        try {
            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
            WikiSummaryResponse resp = restClient.get()
                    .uri("/page/summary/{title}", encoded)
                    .retrieve()
                    .body(WikiSummaryResponse.class);
            return Optional.ofNullable(resp);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public void enrichIfMissing(Reformer reformer) {
        // If there's already a non-placeholder image + bio, keep local content
        boolean hasImage = reformer.getImageUrl() != null && !reformer.getImageUrl().isBlank()
                && !reformer.getImageUrl().contains("via.placeholder.com");
        boolean hasBio = reformer.getDetailedBio() != null && !reformer.getDetailedBio().isBlank();
        if (hasImage && hasBio) {
            return;
        }

        String wikiTitle = ReformerPortraitCatalog.getWikiTitle(reformer.getFullName())
                .orElse(reformer.getFullName());

        fetchSummary(wikiTitle).ifPresent(summary -> {
            if (!hasBio) {
                String extract = safe(summary.extract);
                if (!extract.isBlank()) {
                    reformer.setDetailedBio(extract);
                    reformer.setShortBio(firstSentence(extract).orElse(extract));
                }
            }

            if (!hasImage && summary.thumbnail != null) {
                String src = safe(summary.thumbnail.source);
                if (!src.isBlank()) {
                    reformer.setImageUrl(ReformerPortraitCatalog.compactWikimediaUrl(src));
                }
            }
        });
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private static Optional<String> firstSentence(String text) {
        if (text == null) {
            return Optional.empty();
        }
        int idx = text.indexOf(". ");
        if (idx > 0) {
            return Optional.of(text.substring(0, idx + 1));
        }
        return Optional.empty();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WikiSummaryResponse {
        public String title;
        public String extract;
        public WikiThumbnail thumbnail;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WikiThumbnail {
        public String source;
        public Integer width;
        public Integer height;
    }
}

