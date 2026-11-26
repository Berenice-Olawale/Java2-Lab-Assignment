package org.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TEMPORARY pages so that the whole menu works before the teammate's parts exist.
 * When you implement /chart, /rest or /crud, DELETE the matching method here
 * (two controllers mapping the same URL stop the application from starting).
 */
@Controller
public class PlaceholderController {

    @GetMapping("/rest")
    public String rest(Model model) {
        model.addAttribute("pageTitle", "REST");
        model.addAttribute("active", "rest");
        return "placeholder";
    }

}
