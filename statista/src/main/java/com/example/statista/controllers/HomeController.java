package com.example.statista.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = {"/", "/home"})
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Hello")
                    String name, Model model) {
        model.addAttribute("name", name);
        logger.info("Entered home");
        return "home";
    }
}
