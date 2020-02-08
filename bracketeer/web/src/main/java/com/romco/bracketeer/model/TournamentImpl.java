package com.romco.bracketeer.model;

import com.romco.bracketeer.model.matcher.Matcher;
import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TournamentImpl implements Tournament {
    private List<Participant> participants;
    // todo remove scores and byes, retrieve them dynamically when needed from rounds by participants
    private Map<Participant, Double> startingParticipantScores;
    private Map<Participant, Boolean> participantByes;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;

    private TournamentImpl() {
        this.participants = new ArrayList<>();
        this.startingParticipantScores = new HashMap<>();
        this.rounds = new LinkedList<>();
        this.ruleSet = RuleSet.getDefaultRuleSet();
    }

    /**
     * Creates a TournamentImpl with a RuleSet.getDefaultRuleSet()
     * @param type - TournamentType (Swiss, Round Robin, Single Elim, Double Elim)
     */
    public TournamentImpl(TournamentFormat type) {
        this();
        this.type = type;
        this.ruleSet = RuleSet.getDefaultRuleSet();
    }
    
    public int addParticipants(List<Participant> participants) {
        int count = 0;
        for (Participant participant : participants) {
            addParticipant(participant);
            count++;
        }
        return count;
    }
    
    @Override
    public boolean addParticipant(Participant participant) {
        if (participants.contains(participant)) {
            log.info("Tournament already contains participant {}", participant);
            return false;
        } else {
            log.info("Adding participant {}", participant);
            participants.add(participant);
            startingParticipantScores.put(participant, 0d);
            return true;
        }
    }
    
    @Override
    public boolean removeParticipant(Participant participant) {
        return false;
    }

    public void setStartingScore(Participant participant, double score) {
        startingParticipantScores.put(participant, score);
    }

    public void updateStartingScore(Participant participant, double by) {
        if (startingParticipantScores.containsKey(participant)) {
            startingParticipantScores.put(participant, startingParticipantScores.get(participant) + by);
        } else {
            startingParticipantScores.put(participant, by);
        }
    }

    @Override
    public Round generateNextRound() {
        log.info("Unsorted participants: {}", participants);
        
        Matcher matcher = type.buildMatcher();
        Round round = matcher.generateRound(startingParticipantScores);
        rounds.add(round);

        // set Bye if needed
        for (Match match : round.getMatches()) {
            if (match.isBye()) {
                participantByes.put(match.getParticipants().get(0), true);
            }
        }

        return round;
    }
    
    /**
     * Returns round of specified number (first index is 0)
     * @param n - number of round
     * @return - nth round
     */
    @Override
    public Round getRound(int n) {
        return rounds.get(n);
    }
    
    public List<Round> getRounds() {
        return rounds;
    }
    
    public Participant getParticipant(int n) {
        return participants.get(n);
    }
    
    public boolean setMatchResult(int roundNumber,
                                  Participant participant,
                                  int gamesWon,
                                  int gamesLost) {

        Match match = rounds.get(roundNumber).getMatch(participant);
        
        match.setMatchScore(participant,
                            gamesWon,
                            gamesLost);

        return false;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public Map<Participant, Double> getParticipantScores() {
        Map<Participant, Double> participantScoreMap = new HashMap<>();
        for (Participant participant : participants) {
            for (Round round : rounds) {
                MatchResult result = round.getMatch(participant).getMatchResultForParticipant(participant);
                if (result != null) {
                    // if the map already contains some score, add result to it, otherwise add with first value
                    participantScoreMap.put(participant,
                                            participantScoreMap.containsKey(participant)
                                                    ? participantScoreMap.get(participant) + ruleSet.getPoints(result)
                                                    : ruleSet.getPoints(result));
                }
            }
        }
        return participantScoreMap;
    }
}
