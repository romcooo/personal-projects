package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.ModelAttributeNames;
import com.romco.bracketeer.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT_CODE;

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
    // this needs to be here so that sidenav is shown even on home page if a tournament is in current session
    @ModelAttribute(TOURNAMENT_CODE)
    public String tournamentCode(Model model) {
        if (service.getTournament() != null) {
            String value = service.getTournament().getCode();
            if (value != null) {
                log.debug("tournamentCode from service: {}", value);
                model.addAttribute(ModelAttributeNames.TOURNAMENT_CODE, value);
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
