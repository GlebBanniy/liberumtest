package com.example.liberumtest.controller;

import com.example.liberumtest.service.DataService;
import com.example.liberumtest.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ParserController {

    private final ParserService parserService;
    private final DataService dataService;

    @Autowired
    public ParserController(ParserService parserService, DataService dataService) {
        this.parserService = parserService;
        this.dataService = dataService;
    }

    @GetMapping("/start")
    public String parsFile(Model model) {
        parserService.startParsing("Task.xlsx");
        List<Integer> totalComp1 = new ArrayList<>();
        List<Integer> totalComp2 = new ArrayList<>();
        List<String> companies = dataService.getCompanies();
        for (long i = 1; i < 3; i++) {
            for (long j = 1; j < 3; j++) {
                totalComp1.addAll(parserService.getTotal(j, i, companies.get(0)));
                totalComp2.addAll(parserService.getTotal(j, i, companies.get(1)));
            }
        }
        model.addAttribute("totalComp1", totalComp1);
        model.addAttribute("totalComp2", totalComp2);
        model.addAttribute("data", parserService.getData());
        return "main";
    }
}
