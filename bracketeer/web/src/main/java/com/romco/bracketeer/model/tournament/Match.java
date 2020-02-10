package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.participant.Participant;
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

    private static int idSequence = 1;
    int id;
    private List<Participant> participants;
    private Map<Participant, Integer> scoreMap;
    private int bestOf;
    private boolean isBye;
    private Map <Participant, MatchResult> resultMap;

    public Match() {
        this.id = idSequence++;
        participants = new ArrayList<>();
        resultMap = new HashMap<>();
        scoreMap = new HashMap<>();
    }

    public Match(Participant participant1) {
        this();
        
        participants.add(participant1);
        scoreMap = new HashMap<>(1);
        scoreMap.put(participant1, 1);
        resultMap.put(participant1, MatchResult.WIN);
        
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
        if (scoreMap.containsKey(participant) && getOther(participant) != null) {
            scoreMap.put(participant, gamesWon);
            scoreMap.put(this.getOther(participant), gamesLost);
            if (gamesWon > gamesLost) {
                resultMap.put(participant, MatchResult.WIN);
                resultMap.put(getOther(participant), MatchResult.LOSS);
            } else if (gamesWon < gamesLost) {
                resultMap.put(participant, MatchResult.LOSS);
                resultMap.put(getOther(participant), MatchResult.WIN);
            } else {
                resultMap.put(participant, MatchResult.TIE);
                resultMap.put(getOther(participant), MatchResult.TIE);
            }
            return true;
        } else {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return false;
        }
    }

    public boolean setMatchResultForParticipant(Participant participant, MatchResult matchResultForParticipant) {
        if (resultMap.containsKey(participant)) {
            resultMap.put(participant, matchResultForParticipant);
            // todo put also result for other
            return true;
        } else {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return false;
        }
    }
    
    public MatchResult getMatchResultForParticipant(Participant participant) {
        if (resultMap.containsKey(participant)) {
            return resultMap.get(participant);
        } else {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return null;
        }
    }

    public int getWinCountForParticipant(Participant participant) {
        return scoreMap.get(participant);
    }
    
    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
//        return new ArrayList<>(scoreMap.keySet());
    }
    
    public boolean isBye() {
        return isBye;
    }
}
