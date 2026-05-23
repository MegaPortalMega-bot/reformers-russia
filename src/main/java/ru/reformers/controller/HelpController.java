package ru.reformers.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.reformers.service.HelpDocumentService;
import ru.reformers.service.HelpDocumentService.HelpDocument;

//@Controller
public class HelpController {

    private final HelpDocumentService helpDocumentService;

    public HelpController(HelpDocumentService helpDocumentService) {
        this.helpDocumentService = helpDocumentService;
    }

    @GetMapping("/help")
    public String helpPage(Model model) throws IOException {
        List<HelpDocument> documents = helpDocumentService.listDocuments();
        model.addAttribute("documents", documents);
        model.addAttribute("documentCount", documents.size());
        return "help";
    }
}
