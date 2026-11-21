package org.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Mainpage menu. */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
