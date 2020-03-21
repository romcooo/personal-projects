package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
public class HomeController {
    // == fields
    private final TournamentService service;

    // == constructors
    @Autowired
    public HomeController(TournamentService service) {
        this.service = service;
    }

    // == model attributes
    @ModelAttribute("tournamentCode")
    public String tournamentCode() {
        if (service.getTournament() != null) {
            String value = service.getTournament().getCode();
            if (value != null) {
                log.debug("tournamentCode from service: {}", value);
                return value;
            }
        }
        log.warn("Tournament code is not available, returning null.");
        return null;
    }

    // == request handlers
    // == INDEX
    @GetMapping("/")
    public String index(Model model) {
        log.info("index page loaded");
        return ViewNames.HOME;
    }
}
