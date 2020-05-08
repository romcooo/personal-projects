package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Round {
    private long id;
    private int roundNumber;
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
        log.warn("No match with matchNumber {} found", matchNumber);
        return null;
    }
    
    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                '}';
    }
}
