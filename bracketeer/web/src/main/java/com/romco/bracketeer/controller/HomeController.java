package com.romco.bracketeer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    // == fields

    // == constructors
    @Autowired
    public HomeController() {
    }

    // == model attributes

    // == request handlers
    // == INDEX
    @GetMapping("/")
    public String index(Model model) {
        log.info("index page loaded");
        return "index";
    }
}
