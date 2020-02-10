package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.MainService;
import com.romco.bracketeer.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    // == fields
    private final MainService service;

    // == constructor
    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

     // == request methods
    @GetMapping(ViewNames.Tournament.NEW)
    public String newTournament() {
        log.info("in newTournament");
        log.info(ViewNames.Tournament.NEW);
        return ViewNames.Tournament.NEW;
    }
    
    
}
