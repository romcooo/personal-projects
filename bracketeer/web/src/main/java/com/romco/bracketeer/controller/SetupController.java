package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT_CODE;

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
    public String addPlayer(@PathVariable(name = TOURNAMENT_CODE) String tournamentCode,
                            @RequestParam(name = "playerName") String playerName) {
        log.info("In addPlayer, input: {}, tournamentCode: {}", playerName, tournamentCode);
        service.addPlayer(playerName);
        return Mappings.Tournament.REDIRECT_TO_EXISTING_SETUP.replace("{tournamentCode}", tournamentCode);
    }

    @PostMapping(Mappings.Tournament.REMOVE_PLAYER)
    public String removePlayer(@PathVariable(name = TOURNAMENT_CODE) String tournamentCode,
                               @RequestParam(name = "id") String playerId) {
        log.info("In POST removePlayer with id {}", playerId);
        service.removePlayer(playerId);
        return Mappings.Tournament.REDIRECT_TO_EXISTING_SETUP.replace("{tournamentCode}", tournamentCode);
    }

    @PostMapping(Mappings.Tournament.Setup.SET_POINTS_FOR_MATCH_RESULT_TYPE)
    public String setPointsForMatchResult(@PathVariable(name = TOURNAMENT_CODE) String tournamentCode,
                                          @RequestParam(name = "matchResultType") String matchResultType,
                                          @RequestParam(name = "pointsResult") int pointsForResultType) {
        log.info("In POST setPointsForMatchResult");
        service.setPointsForMatchResultType(matchResultType, pointsForResultType);
        return Mappings.Tournament.REDIRECT_TO_EXISTING_SETUP.replace("{tournamentCode}", tournamentCode);
    }
}
