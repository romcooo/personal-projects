package com.romco.bracketeer.model;

import com.romco.bracketeer.model.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.expression.Lists;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
public class Match {
    int id;
//    private List<Participant> participants;
    private Map<Participant, Integer> resultMap;
    private int bestOf;
    private boolean isBye;
    
    public Match(Participant participant1) {
        resultMap = new HashMap<>(1);
        resultMap.put(participant1, null);
        
        this.isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        resultMap = new HashMap<>(2);
        resultMap.put(participant1, null);
        resultMap.put(participant2, null);
        
        this.isBye = false;
    }
    
    public List<Participant> getOthers(Participant participant) {
        return resultMap.keySet()
                        .stream()
                        .filter((p) -> !p.equals(participant))
                        .peek((p) -> log.debug("After filter: " + p))
                        .collect(Collectors.toList());
    }
    
    public Participant getOther(Participant participant) {
        List<Participant> others = resultMap.keySet()
                                            .stream()
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
    
    public void setResult(Participant participant, int gamesWon) {
        resultMap.put(participant, gamesWon);
    }
    
    public boolean setResult(Participant participant, int gamesWon, int gamesLost) {
        if (resultMap.containsKey(participant) && getOther(participant) != null) {
            resultMap.put(participant, gamesWon);
            resultMap.put(this.getOther(participant), gamesLost);
            return true;
        }
        return false;
    }
    
    public int getResult(Participant participant) {
        return resultMap.get(participant);
    }
    
    public List<Participant> getParticipants() {
        return new ArrayList<>(resultMap.keySet());
    }
    
}
