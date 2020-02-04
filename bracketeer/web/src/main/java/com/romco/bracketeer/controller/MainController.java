package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MainController {

    private final MainService service;

    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

    // == request methods
//    @GetMapping("home")
//    public String welcome() {
//        log.info("user arrived");
//        return ViewNames.HOME;
//    }
}
