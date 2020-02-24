package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private static int idSequence = 1;
    private final int id;
    List<Match> matches;
    
    public Round() {
        this.id = idSequence++;
        this.matches = new ArrayList<>();
    }
    
    public Round(List<Match> matches) {
        this.id = idSequence++;
        this.matches = matches;
    }
    
    public void addMatch(Match toAdd) {
        matches.add(toAdd);
    }
    
    public Match getMatch(int index) {
        return matches.get(index);
    }
    
    public Match getMatch(Participant participant) {
        for (Match match : matches) {
            if (match.getParticipants().contains(participant)) {
                return match;
            }
        }
        return null;
    }
    
    public List<Match> getMatches() {
        return matches;
    }
}
