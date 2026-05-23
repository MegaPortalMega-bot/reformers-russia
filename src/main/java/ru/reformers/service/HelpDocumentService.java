package ru.reformers.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class HelpDocumentService {

    private static final Logger log = LoggerFactory.getLogger(HelpDocumentService.class);

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "docx", "txt");

    private static final Map<String, String> DISPLAY_TITLES = Map.of(
            "reformers_final2_edited.docx", "Методика оценки реформаторов",
            "reformers_tierlist_por_2.docx", "Обоснование выбора реформаторов для сайта",
            "Site_otchet.docx", "Описание сайта и технические решения");

    private List<HelpDocument> documents = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:static/help-docs/*.*");
            
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                if (fileName != null && isAllowedDocument(fileName)) {
                    String title = DISPLAY_TITLES.getOrDefault(fileName, fileName);
                    String url = "/help/files/" + fileName;
                    documents.add(new HelpDocument(fileName, title, url));
                    log.info("Найден документ: {}", fileName);
                }
            }
            
            log.info("Справка: загружено {} документов", documents.size());
        } catch (IOException e) {
            log.error("Ошибка при загрузке документов справки", e);
        }
    }

    public List<HelpDocument> listDocuments() {
        return documents;
    }

    private boolean isAllowedDocument(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot < 0) {
            return false;
        }
        return ALLOWED_EXTENSIONS.contains(fileName.substring(dot + 1).toLowerCase());
    }

    public record HelpDocument(String fileName, String title, String url) {}
}
