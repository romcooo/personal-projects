package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class Round {
    private long id;
    private int roundNumber;
    // note that bestOf is taken from rule set of tournament, but if the ruleset is changed, the existing rounds should
    // actually keep the one they had before, and only newly generated rounds will have the new value
    private int bestOf;
    private List<Match> matches;
    private Tournament ofTournament;
    
    public Round() {
        this.matches = new ArrayList<>();
    }
    
    public Round(List<Match> matches) {
        this.matches = matches;
    }
    
    public void addMatch(Match toAdd) {
        matches.add(toAdd);
    }
    
    public Match getMatch(int index) {
        return matches.get(index);
    }
    
    public Match getMatchByParticipant(Participant participant) {
        for (Match match : matches) {
            if (match.getParticipants().contains(participant)) {
                return match;
            }
        }
        return null;
    }

    public Match getMatchByMatchNumber(int matchNumber) {
        for (Match match : matches) {
            if (match.getMatchNumber() == matchNumber) {
                return match;
            }
        }
        log.warn("No match with matchNumber {} found, matches present: {}", matchNumber, matches.toString());
        return null;
    }

    public List<MatchResult> setMatchResult(int matchNumber, Map<Participant, Integer> gamesWonByParticipants) {
        var m = getMatchByMatchNumber(matchNumber);

        if (m == null) {
            log.warn("no such match exists in this round");
            return Collections.emptyList();
        }

        return m.setMatchScore(gamesWonByParticipants);
    }
    
    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                '}';
    }
}
