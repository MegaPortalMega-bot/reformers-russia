package ru.reformers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.reformers.model.Reformer;
import ru.reformers.service.ReformerService;

@RestController
public class ReformerApiController {

    private final ReformerService reformerService;

    public ReformerApiController(ReformerService reformerService) {
        this.reformerService = reformerService;
    }

    @GetMapping("/api/search")
    public List<Reformer> search(@RequestParam(name = "query", required = false) String query) {
        return reformerService.searchByName(query);
    }
}

