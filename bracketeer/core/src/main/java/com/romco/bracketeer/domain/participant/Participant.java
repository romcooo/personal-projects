package com.romco.bracketeer.domain.participant;


import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.MatchResultEnum;
import com.romco.bracketeer.domain.tournament.RuleSet;
import com.romco.bracketeer.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Participant implements Comparable<Participant> {
    protected static long idCounter = 1;
    protected long id;
    protected String code;
    protected String name;
    protected int numberOfByes;
    // TODO ok so this should actually just be calculated from playedMatches also:
//    protected List<Participant> playedAgainst;
    protected List<Match> playedMatches;
    protected Tournament ofTournament;
    protected double additionalPoints = 0;

    public double getAdditionalPoints() {
        return additionalPoints;
    }

    public void giveAdditionalPoints(double additionalPoints) {
        this.additionalPoints += additionalPoints;
    }

    
    public Participant() {
        id = idCounter++;
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

    // TODO rework this to compute the value here
    public double getScore() {

        // TODO in progress here
        RuleSet ruleSet = ofTournament.getRuleSet();

        double score = additionalPoints;

        if (playedMatches != null
                && !playedMatches.isEmpty()
                && ofTournament != null
                && ofTournament.getRuleSet() != null
                && ofTournament.getRuleSet().getPointMap() != null
//                && ofTournament.getRuleSet().getPointMap().isEmpty()
                && playedMatches.stream().anyMatch(it -> it.getParticipants().contains(this))
                && playedMatches.stream().anyMatch(it -> it.getMatchResult(this) != null)) {

            score = playedMatches.stream()
                                 .mapToDouble(it -> ruleSet.getPointMap()
                                                           .get(it.getMatchResult(this)))
                                 .sum();
        }
        return score;

    }

    public double getScoreAfterRound(int roundNumber) {
        log.debug("in gSAR, rn: {}, this: {}, playedMatches: {}", roundNumber, this.toString(), playedMatches.toString());
        if (roundNumber < 1) {
            return additionalPoints;
        }

        double score = additionalPoints;

        if (playedMatches != null
                && !playedMatches.isEmpty()
                && ofTournament != null
                && ofTournament.getRuleSet() != null
                && ofTournament.getRuleSet().getPointMap() != null
//                && ofTournament.getRuleSet().getPointMap().isEmpty()
                && playedMatches.stream().anyMatch(it -> it.getParticipants().contains(this))
                && playedMatches.stream().anyMatch(it -> it.getMatchResult(this) != null)) {

            RuleSet ruleSet = ofTournament.getRuleSet();

            score = playedMatches.stream()
                                 .filter(match -> match.getOfRound().getRoundNumber() <= roundNumber)
                                 .mapToDouble(it -> ruleSet.getPointMap()
                                                           .get(it.getMatchResult(this)))
                                 .sum();
        }

        return score;
    }
    
    public int getNumberOfByes() {
        return numberOfByes;
    }
    
    public void setNumberOfByes(int numberOfByes) {
        this.numberOfByes = numberOfByes;
    }
    
    public boolean hasPlayedAgainst(Participant other) {
        return this.playedMatches.stream()
                                 .anyMatch(it -> it.getOthers(this)
                                                   .contains(other));
    }

    /*
    Until is inclusive
     */
    public boolean hasPlayedAgainstUntilIncludingRound(Participant other, int roundNumber) {
        log.debug("hasPlayedAgainstUptilRound, playedMatches: {}", playedMatches.toString());
        return this.playedMatches.stream()
                                 .filter(match -> match.getOfRound().getRoundNumber() <= roundNumber)
                                 .anyMatch(match -> match.getParticipants().contains(other));
    }
    
    public void addPlayedMatch(Match match) {
        if (!this.playedMatches.contains(match)) {
            this.playedMatches.add(match);
        }
    }

    public List<Match> getPlayedMatches() {
        return new ArrayList<>(playedMatches);
    }

    
    public Tournament getOfTournament() {
        return ofTournament;
    }
    
    public void setOfTournament(Tournament ofTournament) {
        this.ofTournament = ofTournament;
    }

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
        double score = 0;
        if (ofTournament != null) {

            RuleSet ruleSet = ofTournament.getRuleSet();
            if (playedMatches != null
                    && !playedMatches.isEmpty()
                    && ofTournament != null
                    && ofTournament.getRuleSet() != null
                    && ofTournament.getRuleSet().getPointMap() != null
                    && playedMatches.stream().anyMatch(it -> it.getParticipants().contains(this))
                    && playedMatches.stream().anyMatch(it -> it.getMatchResult(this) != null)) {
//            score = playedMatches.stream()
//                                 .mapToDouble(it -> ofTournament.getRuleSet().getPointMap()
//                                                                .getOrDefault(it.getMatchResult(this), 0d))
//                                 .sum();

                List<MatchResultEnum> matchResultEnums = new ArrayList<>();
                for (Match match : playedMatches) {
                    // TODO figure out why they have null entries in the first place!! without this if, it crashed on nulls
                    if (match.getMatchResult(this) != null) {
                        matchResultEnums.add(match.getMatchResult(this));
                    }
                }
                for (MatchResultEnum resultEnum : matchResultEnums) {
                    score += ruleSet.getPoints(resultEnum);
                }
                log.trace("determining score for participant {}, results: {}, score: {}",
                          name, matchResultEnums.toString(), score);

            }
        }
        return "Participant{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
