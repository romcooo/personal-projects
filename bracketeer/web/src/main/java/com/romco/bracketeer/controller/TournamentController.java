package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public List<Tournament> allTournaments() {
        return service.getAllTournaments();
    }
    
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
    
    // == ADD PLAYER
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
    
    // == REMOVE PLAYER
    
    @GetMapping(Mappings.Tournament.REMOVE_PLAYER)
    public String removePlayer(Model model) {
        log.info("In GET removePlayer");
        model.addAttribute("participants", participants());
        return Mappings.Tournament.NEW;
    }
    
    @PostMapping(Mappings.Tournament.REMOVE_PLAYER)
    public String removePlayer(@RequestParam(name = "id") String playerId) {
        log.info("In POST removePlayer with id {}", playerId);
        service.removePlayer(playerId);
        return Mappings.Tournament.REDIRECT_REMOVE_PLAYER;
    }
    
    @PostMapping(Mappings.Tournament.SAVE)
    public String saveNewTournament() {
        log.info("Saving tournament");
        String code = service.saveTournament();
        // redirect to tournament/{code}
        return Mappings.Tournament.REDIRECT_EXISTING_WITH_ID.replace("{id}", code);
    }
    
    @GetMapping(Mappings.Tournament.EXISTING_WITH_ID)
    public String getTournamentByCode(@PathVariable(value = "id") String code, Model model) {
        log.info("In getTournamentByCode with code {}", code);
        service.getTournamentByCode(code);
        model.addAttribute("participants", participants());
        return Mappings.Tournament.EXISTING;
    }
    
    @GetMapping("/all")
    public String getAllTournaments(Model model) {
        log.info("In getAllTournaments");
        model.addAttribute("tournaments", allTournaments());
        return "tournament/all";
    }
}
