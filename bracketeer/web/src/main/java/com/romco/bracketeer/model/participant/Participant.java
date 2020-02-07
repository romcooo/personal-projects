package com.romco.bracketeer.model.participant;

import com.romco.bracketeer.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Participant implements Comparable<Participant> {
    protected Integer id;
    protected String name;
    protected double score;
    protected boolean hadABye;
    protected List<Participant> playedAgainst;
    protected Map<Integer, Match> playedMatchesByRoundNumber;
    protected List<Match> playedMatches;
    
    public Participant() {
        playedAgainst = new ArrayList<>();
        playedMatches = new ArrayList<>();
    }
    
    public boolean hadABye() {
        return hadABye;
    }
    
    public void setHadABye(boolean hadABye) {
        this.hadABye = hadABye;
    }
    
    public double getScore() {
        return this.score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public void updateScore(int by) {
        score += by;
    }
    
    /**
     * Adds the passed value to the list of Participants against which this player has played,
     * AND calls the passed participant to add this to its "played against" list
     * (so it does not need to be set separately).
     * @param other - the participant against which the participant being called has played
     */
    public void setPlayedAgainstBiDirectional(Participant other) {
        if (!playedAgainst.contains(other)) {
            playedAgainst.add(other);
        }
        other.addPlayedAgainst(this);
    }
    
    public void addPlayedAgainst(Participant other) {
        if (!playedAgainst.contains(other)) {
            playedAgainst.add(other);
        }
    }
    
    public boolean hasPlayedAgainst(Participant other) {
        return this.playedAgainst.contains(other);
    }
    
    public void addPlayedMatch(Match match) {
        this.playedMatches.add(match);
    }
    
    @Override
    public int compareTo(Participant o) {
        if (id == null) {
            return name.compareTo(o.name);
        }
        return id - o.id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Participant) {
            if (id == null) {
                return (name.equals(((Participant) o).name));
            }
            return id == ((Participant) o).id;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{name=" + name +
                ", score=" + score +
                '}';
    }
}
