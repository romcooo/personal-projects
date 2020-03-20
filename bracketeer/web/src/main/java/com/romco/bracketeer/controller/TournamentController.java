package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ViewNames;
import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
    public Collection<Tournament> allTournaments() {
        return service.getAllTournaments();
    }
    public List<Participant> participants() {
        return service.getParticipants();
    }
    
    @ModelAttribute("tournament")
    public Tournament tournament() {
        return service.getTournament();
    }
    
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
    
    // == NEW TOURNAMENT
    @GetMapping(Mappings.Tournament.NEW)
    public String newTournament(Model model) {
        log.info("in newTournament, mapping: {}", Mappings.Tournament.NEW);
        service.createNewTournament();
        // for some reason, tournament needs to be added, otherwise some values are strangely cached on the template
        // specifically, the tournament name bugs out of you change it, then go to all then back to new
        model.addAttribute("tournament", tournament());
        model.addAttribute("participants", participants());
        return ViewNames.Tournament.SETUP;
    }

    // == SETUP OF TOURNAMENT
    @GetMapping(Mappings.Tournament.SETUP)
    public String tournamentSetup(Model model) {
        log.info("In GET tournamentSetup");
        model.addAttribute("participants", participants());
        return ViewNames.Tournament.SETUP;
    }
    
    @PostMapping(Mappings.Tournament.SETUP)
    public String tournamentSetup(@RequestParam(name = "tournamentName", required = false) String tournamentName) {
        log.info("In setName, input: {}", tournamentName);
        if (tournamentName != null) {
            service.setTournamentName(tournamentName);
        }
        return Mappings.Tournament.REDIRECT_TO_SETUP;
    }

    // == save tournament and retrieve existing by code=
    @PostMapping(Mappings.Tournament.SAVE)
    public String saveNewTournament() {
        log.info("Saving tournament");
        String tournamentCode = service.saveTournament();
        // redirect to tournament/{code}
        return Mappings.Tournament.REDIRECT_EXISTING_WITH_CODE.replace("{tournamentCode}", tournamentCode);
    }

    @GetMapping(Mappings.Tournament.EXISTING_WITH_CODE)
    public String getTournamentByCode(@PathVariable(value = "tournamentCode") String tournamentCode, Model model) {
        log.info("In getTournamentByCode with tournamentCode {}", tournamentCode);
        service.getTournamentByCode(tournamentCode);
        model.addAttribute("participants", participants());
//        tournamentCode(); IDK why this doesn't work but below line works...
        model.addAttribute("tournamentCode", tournamentCode);
        return ViewNames.Tournament.SETUP;
    }
    
    // == ALL TOURNAMENTS AND FIND TOURNAMENT
    @GetMapping(Mappings.Tournament.ALL)
    public String getAllTournaments(Model model) {
        log.info("In getAllTournaments");
        model.addAttribute("tournaments", allTournaments());
        return ViewNames.Tournament.ALL;
    }

    @GetMapping(Mappings.Tournament.FIND)
    public String findTournament() {
        log.info("In findTournament");
        return ViewNames.Tournament.FIND;
    }

    

}
