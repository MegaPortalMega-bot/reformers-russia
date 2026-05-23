package ru.reformers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.reformers.service.HelpDocumentService;

@Controller
public class HelpController {

    private final HelpDocumentService helpDocumentService;

    public HelpController(HelpDocumentService helpDocumentService) {
        this.helpDocumentService = helpDocumentService;
    }

    @GetMapping("/help")
    public String helpPage(Model model) {
        model.addAttribute("documents", helpDocumentService.listDocuments());
        model.addAttribute("documentCount", helpDocumentService.listDocuments().size());
        return "help";
    }
}
