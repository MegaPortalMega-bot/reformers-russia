package ru.reformers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ru.reformers.config.HelpDocumentsProperties;

@SpringBootApplication
@EnableConfigurationProperties(HelpDocumentsProperties.class)
public class ReformersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReformersApplication.class, args);
    }
}

