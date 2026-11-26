package org.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestPageController {

    @GetMapping("/rest")
    public String rest(Model model) {
        model.addAttribute("pageTitle", "REST API");
        model.addAttribute("active", "rest");
        return "rest";
    }
}