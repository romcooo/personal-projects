package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private static long idSequence = 1;
    private long id;
    private int roundNumber;
    private List<Match> matches;
    private Tournament ofTournament;
    
    public Round() {
        this.id = idSequence++;
        this.matches = new ArrayList<>();
    }
    
    public Round(List<Match> matches) {
        this.id = idSequence++;
        this.matches = matches;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getRoundNumber() {
        return roundNumber;
    }
    
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }
    
    public Tournament getOfTournament() {
        return ofTournament;
    }
    
    public void setOfTournament(Tournament ofTournament) {
        this.ofTournament = ofTournament;
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
