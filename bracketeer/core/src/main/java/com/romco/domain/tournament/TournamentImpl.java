package com.romco.domain.tournament;

import com.romco.domain.matcher.Matcher;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.util.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class TournamentImpl implements Tournament {
    private static int idCounter;
    private long id;
    private String code; // business id
    private String name;
    private List<Participant> participants;
    private Map<Participant, Double> startingParticipantScores;
    private Map<Participant, Integer> startingParticipantByes;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;

    private TournamentImpl() {
        this.id = idCounter++;
        this.code = CodeGenerator.getCode();
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
    
    @Override
    public long getId() {
        return id;
    }
    
    @Override
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
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
            participant.setCode(participants.size());
            startingParticipantScores.put(participant, 0d);
            startingParticipantByes.put(participant, 0);
            return true;
        }
    }
    
    @Override
    public boolean removeParticipant(int id) {
        for (Participant participant : this.participants) {
            if (participant.getId() == id) {
                participants.remove(participant);
                reconciliateParticipantCodes();
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
        // don't let anyone touch participants outside of this class
        Round round = matcher.generateRound(Collections.unmodifiableList(participants));
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
    
    private void reconciliateParticipantCodes() {
        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            participant.setCode(i+1);
        }
    }
    
    public boolean setMatchResult(int roundNumber,
                                  int participantCode,
                                  int gamesWon,
                                  int gamesLost) {
        for (Participant participant : participants) {
            if (participant.getId() == participantCode) {
                setMatchResult(roundNumber, participant, gamesWon, gamesLost);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Participant> getParticipants() {
        updateParticipants();
        return Collections.unmodifiableList(participants);
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
            if (round.getMatch(participant) != null && round.getMatch(participant).isBye()) {
                byes++;
            }
        }
        return byes;
    }
    
    public List<Participant> getParticipantPlayedAgainst(Participant participant) {
        List<Participant> opponents = new ArrayList<>();
        log.debug("Getting opponents of participant {}", participant);
        for (Round round : rounds) {
            if (round.getMatch(participant) != null) {
                opponents.addAll(round.getMatch(participant).getOthers(participant));
            }
        }
        log.debug("Adding opponents {} for participant {}", opponents, participant);
        return opponents;
    }
    
    // == private methods
    private void updateParticipants() {
        for (Participant participant : this.participants) {
            participant.setScore(getParticipantScore(participant));
            participant.setNumberOfByes(getParticipantByes(participant));
            participant.setPlayedAgainst(getParticipantPlayedAgainst(participant));
        }
    }
}
