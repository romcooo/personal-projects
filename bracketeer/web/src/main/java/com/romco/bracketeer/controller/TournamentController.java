package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ViewNames;
import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Round;
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
//    @ModelAttribute //-- idk I guess this is not really necessary :D
    public Collection<Tournament> allTournaments() {
        return service.getAllTournaments();
    }
    public List<Participant> participants() {
        return service.getParticipants();
    }
    public Tournament tournament() {
        return service.getTournament();
    }
    @ModelAttribute
    public String tournamentCode() {
        if (service.getTournament() != null) {
            String value = service.getTournament().getCode();
            if (value != null) {
                return value;
            }
        }
        log.warn("Tournament code is not available, returning -1.");
        return "-1";
    }

    // == request methods

    // == NEW TOURNAMENT
    @GetMapping(Mappings.Tournament.NEW)
    public String newTournament(Model model) {
        log.info("in newTournament, mapping: {}", Mappings.Tournament.NEW);
        service.createNewTournament();
        model.addAttribute("participants", participants());
        return ViewNames.Tournament.SETUP;
    }

    // == SETUP OF TOURNAMENT
    @GetMapping(Mappings.Tournament.SETUP)
    public String tournamentSetup(Model model) {
        log.info("In GET newTournamentSetup");
        model.addAttribute("participants", participants());
//        if (tournament().getCode() != null) {
//            log.info("code is not null ");
//            model.addAttribute("tournamentCode", tournament().getCode());
//        } else {
//            model.addAttribute("tournamentCode", "1");
//        }
        return ViewNames.Tournament.SETUP;
    }

    // == ADD/REMOVE PLAYERS
    @PostMapping(Mappings.Tournament.ADD_PLAYER)
    public String addPlayer(@RequestParam String playerName) {
        log.info("In addPlayer, input: {}", playerName);
        service.addPlayer(playerName);
        return Mappings.Tournament.REDIRECT_ADD_PLAYER;
    }

    @PostMapping(Mappings.Tournament.REMOVE_PLAYER)
    public String removePlayer(@RequestParam(name = "id") String playerId) {
        log.info("In POST removePlayer with id {}", playerId);
        service.removePlayer(playerId);
        return Mappings.Tournament.REDIRECT_REMOVE_PLAYER;
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

//        if (tournament().getCode() != null) {
//            log.info("code is not null ");
//            model.addAttribute("tournamentCode", tournament().getCode());
//        }
        model.addAttribute("participants", participants());
        return ViewNames.Tournament.SETUP;
    }

    // == GENERATE ROUNDS
    @PostMapping(Mappings.Tournament.Round.GENERATE)
    public String generateRound(@RequestParam(value = "roundNumber") int roundNumber,
                                @PathVariable(value = "tournamentCode") String tournamentCode,
                                Model model) {
        log.info("In generateRound for roundNumber {}", roundNumber);
        service.saveTournament();
        service.generateRound(roundNumber);
        model.addAttribute("tournament", tournament());
        return Mappings.Tournament.Round.REDIRECT_WITH_NUMBER
                .replace("{roundNumber}", Integer.toString(roundNumber))
                .replace("{tournamentCode}", tournament().getCode());
    }

    // == ROUNDS
    @GetMapping(Mappings.Tournament.Round.WITH_NUMBER)
    public String getRound(@PathVariable(value = "roundNumber") int roundNumber, Model model) {
        log.info("In getRound with roundNumber {}", roundNumber);
        Round round = service.getTournament().getRound(roundNumber);
        model.addAttribute("round", round);
        return ViewNames.Tournament.ROUND;
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
