package com.romco.bracketeer.controller;

import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.participant.Player;
import com.romco.bracketeer.model.tournament.Tournament;
import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class TournamentController {

    // == fields
    private final TournamentService service;

    // == constructor
    @Autowired
    public TournamentController(TournamentService service) {
        this.service = service;
    }
    
    // == model attributes
    @ModelAttribute
    public List<Participant> participants() {
        return service.getParticipants();
    }
    
    @ModelAttribute
    public Tournament tournament() {
        return service.getTournament();
    }

    // == request methods
    @GetMapping(Mappings.Tournament.NEW)
    public String newTournament(Model model) {
        log.info("in newTournament, mapping: {}", Mappings.Tournament.NEW);
        model.addAttribute("participants", participants());
        return Mappings.Tournament.NEW;
    }
    
    @GetMapping(Mappings.Tournament.ADD_PLAYER)
    public String addPlayer(Model model) {
        log.info("In GET addPlayer");
        model.addAttribute("participants", participants());
        return Mappings.Tournament.NEW;
    }
    
    @PostMapping(Mappings.Tournament.ADD_PLAYER)
    public String addPlayer(@RequestParam String playerName) {
        log.info("In addPlayer, input: {}", playerName);
        service.addPlayer(playerName);
        return Mappings.Tournament.REDIRECT_ADD_PLAYER;
    }
    
}
