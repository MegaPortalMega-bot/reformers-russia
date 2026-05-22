package ru.reformers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reformers.help")
public class HelpDocumentsProperties {

    /** Абсолютный путь к папке с документами справки. */
    private String documentsPath = "/home/sanyamopzzz/Реформаторы_Документы";

    public String getDocumentsPath() {
        return documentsPath;
    }

    public void setDocumentsPath(String documentsPath) {
        this.documentsPath = documentsPath;
    }
}
