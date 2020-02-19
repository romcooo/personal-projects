package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.matcher.Matcher;
import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.*;

@Slf4j
@Component
public class TournamentImpl implements Tournament {
    private static int idCounter;
    private final int id;
    private String name;
    private List<Participant> participants;
    private Map<Participant, Double> startingParticipantScores;
    private Map<Participant, Integer> startingParticipantByes;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;

    private TournamentImpl() {
        this.id = idCounter++;
        this.name = "";
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
    
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
            startingParticipantByes.put(participant, 0);
            return true;
        }
    }
    
    // TODO
    @Override
    public boolean removeParticipant(int id) {
        for (Participant participant : this.participants) {
            if (participant.getId() == id) {
                participants.remove(participant);
                return true;
            }
        }
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
    
        updateParticipants();
        
        Matcher matcher = type.buildMatcher();
        Round round = matcher.generateRound(participants);
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
    
    public boolean setMatchResult(int roundNumber,
                                  int participantId,
                                  int gamesWon,
                                  int gamesLost) {
        for (Participant participant : participants) {
            if (participant.getId() == participantId) {
                setMatchResult(roundNumber, participant, gamesWon, gamesLost);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Participant> getParticipants() {
        updateParticipants();
        return participants;
    }
    
    public Double getParticipantScore(Participant participant) {
        double score = startingParticipantScores.get(participant);
        for (Round round : rounds) {
            MatchResult result = round.getMatch(participant).getMatchResultForParticipant(participant);
            if (result != null) {
                score += ruleSet.getPoints(result);
                log.debug("Result for participant {} is {}, score set to {}", participant, result, score);
            }
        }
        log.debug("Score of participant {} is {}", participant, score);
        return score;
    }
    
    public Integer getParticipantByes(Participant participant) {
        int byes = startingParticipantByes.get(participant);
        for (Round round : rounds) {
            if(round.getMatch(participant) != null && round.getMatch(participant).isBye()) {
                byes++;
            }
        }
        return byes;
    }
    
    // == private methods
    private void updateParticipants() {
        for (Participant participant : this.participants) {
            participant.setScore(getParticipantScore(participant));
            participant.setNumberOfByes(getParticipantByes(participant));
        }
    }
}
