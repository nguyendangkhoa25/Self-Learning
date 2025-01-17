package com.nguyendangkhoa25.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @Value("${spring.application.developer.name}")
    private String yourName;
    @GetMapping("/")
    public String welcomeMessage(Model model) {
        model.addAttribute("yourName", yourName);
        return "welcome";
    }
}
