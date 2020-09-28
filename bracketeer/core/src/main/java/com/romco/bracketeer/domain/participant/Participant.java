package com.romco.bracketeer.domain.participant;


import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.MatchResultEnum;
import com.romco.bracketeer.domain.tournament.Tournament;

import java.util.ArrayList;
import java.util.List;

public class Participant implements Comparable<Participant> {
    protected static long idCounter = 1;
    protected long id;
    protected String code;
    protected String name;
    protected double score;
    protected int numberOfByes;
    protected List<Participant> playedAgainst;
    protected List<Match> playedMatches;
    protected Tournament ofTournament;
    
    public Participant() {
        id = idCounter++;
        playedAgainst = new ArrayList<>();
        playedMatches = new ArrayList<>();
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }

    public String setCode(String code) {
        String previous = this.code;
        this.code = code;
        return previous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
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

    public List<Match> getPlayedMatches() {
        return new ArrayList<>(playedMatches);
    }

    public void setPlayedMatches(List<Match> matches) {
        this.playedMatches = new ArrayList<>(matches);
    }
    
    public Tournament getOfTournament() {
        return ofTournament;
    }
    
    public void setOfTournament(Tournament ofTournament) {
        this.ofTournament = ofTournament;
    }

    // replaced by generic below, remove later if the generic method proves as usable
//    public long getNumberOfWins() {
//        return playedMatches
//                .stream()
//                .filter(it -> it.getMatchResult(this) == MatchResultEnum.WIN)
//                .count();
//    }

    public long getTotalNumberOfPlayedMatches() {
        // null means the participant has not yet played that match => filter out
        return playedMatches.stream()
                            .filter(it -> it.getMatchResult(this) != null)
                            .count();
    }

    public long getNumberOfMatchesWithResult(MatchResultEnum resultType) {
        return playedMatches
                .stream()
                .filter(it -> it.getMatchResult(this) == resultType)
                .count();
    }

    public long getNumberOfMatchesWithResult(String resultType) {
        return getNumberOfMatchesWithResult(MatchResultEnum.valueOf(resultType));
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
        return "Participant{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
