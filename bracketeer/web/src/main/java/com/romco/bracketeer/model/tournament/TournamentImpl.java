package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.matcher.Matcher;
import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TournamentImpl implements Tournament {
    private List<Participant> participants;
    private Map<Participant, Double> startingParticipantScores;
    private Map<Participant, Integer> startingParticipantByes;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;

    private TournamentImpl() {
        this.participants = new ArrayList<>();
        this.startingParticipantScores = new HashMap<>();
        this.startingParticipantByes = new HashMap<>();
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
    
    // TODO maybe implement later
//    public int addParticipants(List<Participant> participants) {
//        int count = 0;
//        for (Participant participant : participants) {
//            addParticipant(participant);
//            count++;
//        }
//        return count;
//    }
    
    @Override
    public boolean addParticipant(Participant participant) {
        if (participants.contains(participant)) {
            log.info("Tournament already contains participant {}", participant);
            return false;
        } else {
            log.info("Adding participant {}", participant);
            participants.add(participant);
            startingParticipantScores.put(participant, 0d);
            startingParticipantByes.put(participant, 0);
            return true;
        }
    }
    
    // TODO
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
        Round round = matcher.generateRound(participants, getParticipantScores(), getParticipantByes());
        rounds.add(round);

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

    @Override
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

    @Override
    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public Map<Participant, Integer> getParticipantByes() {
        Map<Participant, Integer> participantByes = new HashMap<>();
        for (Participant participant : participants) {
            int numberOfByes = 0;
            for (Round round : rounds) {
                if (round.getMatch(participant).isBye()) {
                    numberOfByes++;
                }
            }
            participantByes.put(participant, numberOfByes);
        }
        return participantByes;
    }

    @Override
    public Map<Participant, Double> getParticipantScores() {
        Map<Participant, Double> participantScoreMap = new HashMap<>();
        for (Participant participant : participants) {
            participantScoreMap.put(participant, startingParticipantScores.get(participant));
            
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
