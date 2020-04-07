package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.TournamentService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ViewNames;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT;
import static com.romco.bracketeer.util.ModelAttributeNames.TOURNAMENT_CODE;

@Slf4j
@Controller
public class BracketController {

    // == fields
    private final TournamentService service;

    // == constructor
    @Autowired
    public BracketController(TournamentService service) {
        this.service = service;
    }

    // == model attributes
    @ModelAttribute(TOURNAMENT)
    public Tournament tournament(Model model) {
        Tournament tournament = service.getTournament();
        model.addAttribute(TOURNAMENT, tournament);
        return tournament;
    }

    // == request handlers
    @GetMapping(Mappings.Tournament.BRACKET)
    public String getBracket(@PathVariable(value = TOURNAMENT_CODE) String tournamentCode,
                             Model model) {
        Tournament tournament = tournament(model);
        log.info("In getBracket with tournamentCode {}, tournament type: {}",
                 tournamentCode,
                 tournament.getRuleSet().getType());

        if (tournament.getRuleSet().getType() != TournamentFormat.SINGLE_ELIMINATION
                && tournament.getRuleSet().getType() != TournamentFormat.DOUBLE_ELIMINATION) {
            model.addAttribute("message",
                               "This page is only available for Single/Double Elimination tournaments");
            return ViewNames.ERROR_WITH_MESSAGE;
        }

        model.addAttribute("numberOfRounds", service.getMaxNumberOfRounds());
        return ViewNames.Tournament.BRACKET_VISUALISATION;
    }
}
