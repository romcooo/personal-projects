package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ViewNames;
import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Round;
import com.romco.domain.tournament.Standings;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT;
import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT_CODE;

@Slf4j
@Controller
public class RoundController {

    // == fields
    private final TournamentService service;

    // == constructors
    @Autowired
    public RoundController(TournamentService service) {
        this.service = service;
    }

    // == model attributes
    @ModelAttribute(TOURNAMENT)
    public Tournament tournament() {
        return service.getTournament();
    }
    @ModelAttribute(TOURNAMENT_CODE)
    public String tournamentCode(Model model) {
        if (service.getTournament() != null) {
            String value = service.getTournament().getCode();
            if (value != null) {
                model.addAttribute(TOURNAMENT_CODE, value);
                return value;
            }
        }
        log.warn("Tournament code is not available, returning null.");
        return null;
    }

    // == request handlers
    // == ROUNDS
    @PostMapping(Mappings.Tournament.Round.GENERATE)
    public String generateRound(@RequestParam(value = "roundNumber") int roundNumber,
                                @PathVariable(value = TOURNAMENT_CODE) String tournamentCode,
                                Model model) {
        log.info("In generateRound for roundNumber {}", roundNumber);

        service.getTournamentByCode(tournamentCode);

        service.saveTournament();
        service.generateRound(roundNumber);
        model.addAttribute(TOURNAMENT, tournament());
        return Mappings.Tournament.Round.REDIRECT_WITH_NUMBER
                .replace("{roundNumber}", Integer.toString(roundNumber))
                .replace("{tournamentCode}", tournament().getCode());
    }

    @GetMapping(Mappings.Tournament.Round.WITH_NUMBER)
    public String getRound(@PathVariable(value = "roundNumber") int roundNumber,
                           @PathVariable(value = TOURNAMENT_CODE) String tournamentCode,
                           Model model) {
        log.info("In getRound with roundNumber {}", roundNumber);

        service.getTournamentByCode(tournamentCode);

        Round round = service.getTournament().getRound(roundNumber);

        if (round == null) {
            model.addAttribute("message",
                               "Round #" + roundNumber + " does not yet exist for this tournament.");
            return ViewNames.ERROR_WITH_MESSAGE;
        }

        model.addAttribute("round", round);
        tournamentCode(model);
        model.addAttribute(TOURNAMENT_CODE, tournamentCode);
        return ViewNames.Tournament.ROUND;
    }

    // == MATCH RESULTS
    @PostMapping(Mappings.Tournament.Round.Match.RESULT)
    public String postMatchResult(
            @PathVariable(value = "roundNumber") int roundNumber,
            @PathVariable(value = "matchNumber") int matchNumber,
            @RequestParam(value = "participant1Score") int participant1Score,
            @RequestParam(value = "participant2Score") int participant2Score,
            Model model) {
        String participant1Code = tournament().getRound(roundNumber)
                                              .getMatch(matchNumber - 1)
                                              .getParticipants()
                                              .get(0)
                                              .getCode();
        String participant2Code = tournament().getRound(roundNumber)
                                              .getMatch(matchNumber - 1)
                                              .getParticipants()
                                              .get(1)
                                              .getCode();
        log.info("In postMatchResult for roundNumber {}, matchNumber {}, scores are {} : {}",
                 roundNumber,
                 matchNumber,
                 participant1Score,
                 participant2Score);
        // this is a version of the method that allows to use both scores with only 1 participant,
        // assuming that it's a duel.
        service.setResult(roundNumber, participant1Code, participant1Score, participant2Score);

        return Mappings.Tournament.Round.REDIRECT_WITH_NUMBER;
    }

    // == STANDINGS AFTER ROUND #
    @GetMapping(Mappings.Tournament.Round.STANDINGS)
    public String getStandingsAfterRound(
            @PathVariable(value = TOURNAMENT_CODE) String tournamentCode,
            @PathVariable(value = "roundNumber") int roundNumber,
            Model model) {

        log.info("In getStandingsAfterRound with roundNumber: {} for tournamentCode: {}",
                 roundNumber,
                 tournamentCode);

        if (tournament() == null ||
                (tournament().getCode() != null && !tournament().getCode().equals(tournamentCode))) {
            service.getTournamentByCode(tournamentCode);
        }

        Round round = service.getTournament().getRound(roundNumber);

        if (round == null) {
            model.addAttribute("message", "No rounds yet exist for this tournament.");
            return ViewNames.ERROR_WITH_MESSAGE;
        }

        model.addAttribute("round", round);

//        List<Participant> participantsAfterRound = service.getTournament().getParticipantsForAfterRound(roundNumber);
        List<Participant> participantsAfterRound = Standings.getStandings(service.getTournament(), roundNumber);

        model.addAttribute("participants", participantsAfterRound);

        return ViewNames.Tournament.STANDINGS;
    }

}
