package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
public class Match {
    public static final String PARTICIPANT_NOT_FOUND_WARN_MESSAGE = "Match doesn't contain the provided participant: {}";

    private long id;
    private List<Participant> participants;
    private int bestOf;
    private boolean isBye;

    private Map<Participant, Integer> scoreMap;
    private Map<Participant, MatchResultEnum> resultMap;
    
    private Map<Participant, MatchResult> matchResults;
    private Round ofRound;

    public Match() {
        participants = new ArrayList<>();
        resultMap = new HashMap<>();
        scoreMap = new HashMap<>();
        matchResults = new HashMap<>();
    }

    public Match(Participant participant1) {
        this();
        
        participants.add(participant1);
        scoreMap = new HashMap<>(1);
        scoreMap.put(participant1, 1);
        resultMap.put(participant1, MatchResultEnum.WIN);
        
        this.isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this();
    
        participants.add(participant1);
        participants.add(participant2);
        scoreMap = new HashMap<>(2);
        scoreMap.put(participant1, null);
        scoreMap.put(participant2, null);

        this.isBye = false;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getBestOf() {
        return bestOf;
    }
    
    public void setBestOf(int bestOf) {
        this.bestOf = bestOf;
    }
    
    public void setBye(boolean bye) {
        isBye = bye;
    }
    
    public Round getOfRound() {
        return ofRound;
    }
    
    public void setOfRound(Round ofRound) {
        this.ofRound = ofRound;
    }
    
    public List<Participant> getOthers(Participant participant) {
        return participants.stream()
                    .filter((p) -> !p.equals(participant))
                    .peek((p) -> log.debug("After filter: " + p))
                    .collect(Collectors.toList());
        
//        return scoreMap.keySet()
//                       .stream()
//                       .filter((p) -> !p.equals(participant))
//                       .peek((p) -> log.debug("After filter: " + p))
//                       .collect(Collectors.toList());
    }
    
    public Participant getOther(Participant participant) {
        List<Participant> others = participants.stream()
                                               .filter((p) -> !p.equals(participant))
                                               .collect(Collectors.toList());
        
        if (others.size() > 1) {
            log.warn("getOther yielded more than one result");
        }
        if (others.isEmpty()) {
            log.warn("getOther yielded 0 results");
            return null;
        }
        
        return others.get(0);
    }
    
    public boolean setMatchScore(Participant participant, int gamesWon, int gamesLost) {
        // new
        // if participant is not of this match
        if (!participants.contains(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return false;
        }
        MatchResult matchResult = new MatchResult(this, participant, gamesWon, gamesLost);
        MatchResult other = new MatchResult(this, getOther(participant), gamesLost, gamesWon);
        matchResults.put(participant, matchResult);
        matchResults.put(getOther(participant), other);
        return true;

        // old
//        if (scoreMap.containsKey(participant) && getOther(participant) != null) {
//            scoreMap.put(participant, gamesWon);
//            scoreMap.put(this.getOther(participant), gamesLost);
//            if (gamesWon > gamesLost) {
//                resultMap.put(participant, MatchResultEnum.WIN);
//                resultMap.put(getOther(participant), MatchResultEnum.LOSS);
//            } else if (gamesWon < gamesLost) {
//                resultMap.put(participant, MatchResultEnum.LOSS);
//                resultMap.put(getOther(participant), MatchResultEnum.WIN);
//            } else {
//                resultMap.put(participant, MatchResultEnum.TIE);
//                resultMap.put(getOther(participant), MatchResultEnum.TIE);
//            }
//            return true;
//        } else {
//            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
//            return false;
//        }
    }
    
    public MatchResult getMatchResult(Participant participant) {
        if (matchResults.containsKey(participant)) {
            return matchResults.get(participant);
        } else {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return null;
        }

//        if (resultMap.containsKey(participant)) {
//            return resultMap.get(participant);
//        } else {
//            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
//            return null;
//        }
    }
    
    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
//        return new ArrayList<>(scoreMap.keySet());
    }
    
    public boolean isBye() {
        return isBye;
    }
}
