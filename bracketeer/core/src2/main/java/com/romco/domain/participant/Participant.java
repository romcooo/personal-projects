package com.romco.domain.participant;

<<<<<<< HEAD:bracketeer/core/src/main/java/com/romco/domain/participant/Participant.java
=======


>>>>>>> be88e0fe0ef736522434cea5e7abdfdce757de22:bracketeer/core/src2/main/java/com/romco/domain/participant/Participant.java
import com.romco.domain.tournament.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Participant implements Comparable<Participant> {
    protected static long idCounter = 1;
    protected long id;
    protected int code;
    protected String name;
    protected double score;
    protected int numberOfByes;
    protected List<Participant> playedAgainst;
//    protected Map<Integer, Match> playedMatchesByRoundNumber;
    protected List<Match> playedMatches;
    
    public Participant() {
        id = idCounter++;
        playedAgainst = new ArrayList<>();
        playedMatches = new ArrayList<>();
    }
    
    public long getId() {
        return id;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public double getScore() {
        return score;
    }
    
    public int setCode(int code) {
        int previous = this.code;
        this.code = code;
        return previous;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public int getNumberOfByes() {
        return numberOfByes;
    }
    
    public void setNumberOfByes(int numberOfByes) {
        this.numberOfByes = numberOfByes;
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
    
    public void setPlayedAgainst(List<Participant> opponents) {
        this.playedAgainst = opponents;
    }
    
    public void addPlayedMatch(Match match) {
        this.playedMatches.add(match);
    }
    
    @Override
    public int compareTo(Participant o) {
        return Long.compare(id, o.id);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Participant) {
            return id == (((Participant) o).id);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{id=" + id +
                ", name=" + name + '}';
    }
}
