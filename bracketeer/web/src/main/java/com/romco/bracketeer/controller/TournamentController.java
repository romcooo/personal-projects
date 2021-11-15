package com.romco.bracketeer.controller;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Tournament;
import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ModelAttributeNames;
import com.romco.bracketeer.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT;
import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT_CODE;

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
    
    @ModelAttribute(TOURNAMENT)
    public Tournament tournament(Model model) {
        Tournament tournament = service.getTournament();
        model.addAttribute(TOURNAMENT, tournament);
        return tournament;
    }
    
    @ModelAttribute(TOURNAMENT_CODE)
    public String tournamentCode(Model model, HttpSession session) {
        if (service.getTournament() != null) {
            String code = service.getTournament().getCode();
            if (code != null) {
                log.debug("tournamentCode from service: {}", code);
//                session.setAttribute(TOURNAMENT_CODE, code);
                model.addAttribute(TOURNAMENT_CODE, code);
                return code;
            }
        }
        log.warn("Tournament code is not available, returning null.");
        return null;
    }

    // == request handlers
    
    // == NEW TOURNAMENT
    @GetMapping(Mappings.Tournament.NEW)
    public String newTournament(Model model, HttpSession session) {
        log.info("in newTournament, mapping: {}", Mappings.Tournament.NEW);
        service.createNewTournament();
        // for some reason, tournament needs to be added, otherwise some values are strangely cached on the template
        // specifically, the tournament name bugs out of you change it, then go to all then back to new
        Tournament tournament = tournament(model);
        session.setAttribute(TOURNAMENT_CODE, tournament.getCode());
//        model.addAttribute(TOURNAMENT_CODE, tournament.getCode());
        model.addAttribute(ModelAttributeNames.PARTICIPANTS, participants());
        return Mappings.Tournament.REDIRECT_EXISTING_WITH_CODE.replace("{tournamentCode}", tournament.getCode());
    }

    //  TODO move setup to setup controller
    // == SETUP OF TOURNAMENT
    @GetMapping({
//            Mappings.Tournament.SETUP,
            Mappings.Tournament.Setup.BASE})
    public String tournamentSetup(@PathVariable(name = TOURNAMENT_CODE, required = false) String tournamentCode,
                                  Model model) {
        if (tournamentCode != null) {
            service.getTournamentByCode(tournamentCode);
        }
        log.info("In GET tournamentSetup");
        model.addAttribute("participants", participants());
        return ViewNames.Tournament.SETUP;
    }
    
    @PostMapping({
//            Mappings.Tournament.SETUP,
            Mappings.Tournament.Setup.BASE})
    public String tournamentSetup(@PathVariable(name = TOURNAMENT_CODE, required = false) String tournamentCode,
                                  @RequestParam(name = "tournamentName", required = false) String tournamentName,
                                  @RequestParam(name = "tournamentType", required = false) String tournamentType) {
        log.info("In tournamentSetup, tournamentName: {}, tournamentType: {}", tournamentName, tournamentType);
        if (tournamentCode != null) {
            service.getTournamentByCode(tournamentCode);
        }
        if (tournamentName != null) {
            service.setTournamentName(tournamentName);
        }
        if (tournamentType != null) {
            service.setTournamentType(tournamentType);
        }
        return Mappings.Tournament.Setup.REDIRECT_TO_EXISTING_SETUP;
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
    public String getTournamentByCode(@PathVariable(value = TOURNAMENT_CODE) String tournamentCode, Model model, HttpSession session) {
        log.info("In getTournamentByCode with tournamentCode {}", tournamentCode);
        service.getTournamentByCode(tournamentCode);
        model.addAttribute("participants", participants());
        tournamentCode(model, session);
        session.setAttribute(TOURNAMENT_CODE, tournamentCode);
//        model.addAttribute(TOURNAMENT_CODE, tournamentCode);
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
