package ru.reformers.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import ru.reformers.config.HelpDocumentsProperties;

//@Service
public class HelpDocumentService {

    private static final Logger log = LoggerFactory.getLogger(HelpDocumentService.class);

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "docx", "txt");

    private static final Map<String, String> DISPLAY_TITLES = Map.of(
            "reformers_final2_edited.docx", "Методика оценки реформаторов",
            "reformers_tierlist_por_2.docx", "Обоснование выбора реформаторов для сайта",
            "Site_otchet.docx", "Описание сайта и технические решения");

    private static final String FILES_URL_PREFIX = "/help/files/";

    @Value("${reformers.help.documents-path:/home/sanyamopzzz/Реформаторы_Документы}")
    private String configuredPath;

    private final HelpDocumentsProperties helpProperties;

    private Path documentsDir;

    public HelpDocumentService(HelpDocumentsProperties helpProperties) {
        this.helpProperties = helpProperties;
    }

    @PostConstruct
    void initDocumentsDir() throws IOException {
        this.documentsDir = resolveDocumentsDir();
        log.info("Справка: каталог документов = {}", documentsDir);
        log.info("Справка: найдено файлов = {}", listDocuments().size());
    }

    public Path getDocumentsDir() {
        if (documentsDir == null) {
            try {
                documentsDir = resolveDocumentsDir();
            } catch (IOException e) {
                throw new IllegalStateException("Не удалось определить каталог справки", e);
            }
        }
        return documentsDir;
    }

    public String getFilesUrlPrefix() {
        return FILES_URL_PREFIX;
    }

    private Path resolveDocumentsDir() throws IOException {
        List<String> candidates = List.of(
                configuredPath,
                helpProperties.getDocumentsPath(),
                Path.of(System.getProperty("user.home"), "Реформаторы_Документы").toString(),
                Path.of(System.getProperty("user.dir"), "help-documents").toString(),
                Path.of(System.getProperty("user.dir"), "src", "main", "resources", "static", "help-docs").toString());

        for (String candidate : candidates) {
            if (candidate == null || candidate.isBlank()) {
                continue;
            }
            Path path = Path.of(candidate.trim()).toAbsolutePath().normalize();
            if (!Files.isDirectory(path)) {
                continue;
            }
            boolean hasDocuments;
            try (Stream<Path> stream = Files.list(path)) {
                hasDocuments = stream
                        .filter(Files::isRegularFile)
                        .anyMatch(this::isAllowedDocument);
            }
            if (hasDocuments) {
                return path;
            }
        }

        Path fallback = Path.of(configuredPath).toAbsolutePath().normalize();
        if (!Files.isDirectory(fallback)) {
            Files.createDirectories(fallback);
        }
        return fallback;
    }

    public List<HelpDocument> listDocuments() throws IOException {
        if (documentsDir == null) {
            documentsDir = resolveDocumentsDir();
        }

        File dir = documentsDir.toFile();
        if (!dir.isDirectory()) {
            log.warn("Справка: каталог не найден: {}", documentsDir);
            return List.of();
        }

        File[] files = dir.listFiles(file -> file.isFile() && isAllowedDocument(file.toPath()));
        if (files == null || files.length == 0) {
            log.warn("Справка: в каталоге {} нет подходящих файлов", documentsDir);
            return List.of();
        }

        return Arrays.stream(files)
                .sorted(Comparator.comparing(f -> f.getName().toLowerCase(Locale.ROOT)))
                .map(file -> {
                    String fileName = file.getName();
                    String title = DISPLAY_TITLES.getOrDefault(fileName, fileName);
                    return new HelpDocument(fileName, title, FILES_URL_PREFIX + fileName);
                })
                .toList();
    }

    private boolean isAllowedDocument(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        int dot = name.lastIndexOf('.');
        if (dot < 0) {
            return false;
        }
        return ALLOWED_EXTENSIONS.contains(name.substring(dot + 1));
    }

    public record HelpDocument(String fileName, String title, String url) {
    }
}
