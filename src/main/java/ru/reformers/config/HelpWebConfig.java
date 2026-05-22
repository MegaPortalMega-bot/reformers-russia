package ru.reformers.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ru.reformers.service.HelpDocumentService;

@Configuration
@EnableConfigurationProperties(HelpDocumentsProperties.class)
public class HelpWebConfig implements WebMvcConfigurer {

    private final HelpDocumentService helpDocumentService;

    public HelpWebConfig(HelpDocumentService helpDocumentService) {
        this.helpDocumentService = helpDocumentService;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dir = helpDocumentService.getDocumentsDir().toAbsolutePath().toString();
        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        registry.addResourceHandler("/help/files/**")
                .addResourceLocations("file:" + dir);
    }
}
