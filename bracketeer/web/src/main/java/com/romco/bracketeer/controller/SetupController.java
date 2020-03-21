package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class SetupController {

    // == fields
    private final TournamentService service;

    // == constructors
    @Autowired
    public SetupController(TournamentService service) {
        this.service = service;
    }

    // == request handlers
    @PostMapping(Mappings.Tournament.ADD_PLAYER)
    public String addPlayer(@RequestParam(name = "playerName") String playerName) {
        log.info("In addPlayer, input: {}", playerName);
        service.addPlayer(playerName);
        return Mappings.Tournament.REDIRECT_TO_SETUP;
    }

    @PostMapping(Mappings.Tournament.REMOVE_PLAYER)
    public String removePlayer(@RequestParam(name = "id") String playerId) {
        log.info("In POST removePlayer with id {}", playerId);
        service.removePlayer(playerId);
        return Mappings.Tournament.REDIRECT_TO_SETUP;
    }

}
