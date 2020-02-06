package com.romco.bracketeer.model;

import java.util.List;

public class Round {
    List<Match> matches;
    
    public Round(List<Match> matches) {
        this.matches = matches;
    }
    
    public Round() {
    }
    
    public void addMatch(Match toAdd) {
        matches.add(toAdd);
    }
    
    public Match getMatch(int index) {
        return matches.get(index);
    }
    
    
}
