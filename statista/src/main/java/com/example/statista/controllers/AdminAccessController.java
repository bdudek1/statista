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
public class AdminAccessController {

    public static final Logger logger = LoggerFactory.getLogger(AdminAccessController.class);

    @GetMapping(value = {"/admin"})
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Hello")
                    String name, Model model) {
        model.addAttribute("name", name);
        logger.info("Entered admin");
        return "admin";
    }
}
