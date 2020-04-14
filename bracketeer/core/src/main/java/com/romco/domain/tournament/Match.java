package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Match {
    public static final String PARTICIPANT_NOT_FOUND_WARN_MESSAGE
            = "Match doesn't contain the provided participant: {}";

    private long id;
    private Round ofRound;
    private int matchNumber;
    private boolean isBye;
    private Map<Participant, MatchResult> matchResultMap;

    public Match() {
        matchResultMap = new HashMap<>();
    }

    public Match(Participant participant1) {
        this();
        addParticipant(participant1);
        isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this();
        addParticipant(participant1);
        addParticipant(participant2);
        isBye = false;
    }

//    public boolean setWinsByParticipantCode(String participantCode, int gamesWon) {
//        for (Participant participant : participants) {
//            if (participant.getCode().equals(participantCode)) {
//
//                MatchResult matchResult1 = new MatchResult(this, participant, gamesWon);
//                matchResultsForParticipant.put(participant, matchResult1);
//                return true;
//            }
//        }
//        log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participantCode);
//        return false;
//    }

    public List<Participant> getOthers(Participant participant) {
        return matchResultMap.keySet().stream()
                             .filter((p) -> !p.equals(participant))
                             .peek((p) -> log.debug("After filter: " + p))
                             .collect(Collectors.toList());
    }
    
    public Participant getOther(Participant participant) {
        List<Participant> others = matchResultMap.keySet().stream()
                                                 .filter((p) -> !p.equals(participant))
                                                 .collect(Collectors.toList());
        
        if (others.size() > 1) {
            log.warn("getOther yielded more than one result");
        }
        if (others.isEmpty()) {
            log.warn("getOther yielded 0 results");
            return null;
        }
        
        log.debug("In getOther, participant: {}, other: {}", participant, others.get(0));
        
        return others.get(0);
    }

    public List<MatchResult> setMatchScore(Participant participant, int gamesWon) {
        
        if (!matchResultMap.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return Collections.emptyList();
        }
        
        MatchResult matchResult = matchResultMap.get(participant);
        matchResult.setGamesWon(gamesWon);
    
        List<MatchResult> matchResults = new ArrayList<>();
        matchResults.add(matchResult);
        return matchResults;
    }

    public List<MatchResult> setMatchScore(Participant participant, int gamesWon, int gamesLost) {
        // if participant is not of this match
        if (!matchResultMap.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return Collections.emptyList();
        }
        Participant otherParticipant = getOther(participant);

        MatchResult matchResult = matchResultMap.get(participant);
        matchResult.setGamesWon(gamesWon);
        MatchResult otherMatchResult = matchResultMap.get(otherParticipant);
        otherMatchResult.setGamesWon(gamesLost);
        log.debug("In setMatchScore, creating matchResults: {} and {}", matchResult, otherMatchResult);
        
        List<MatchResult> matchResults = new ArrayList<>();
        // the order of the elements should be kept so that
        // the first result is the one with input participant
        matchResults.add(matchResult);
        matchResults.add(otherMatchResult);
        return matchResults;
    }
    
    public MatchResultEnum getMatchResult(Participant participant) {
        if (!matchResultMap.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return null;
        }
        // if the other entry is null, this means that no result is present yet
        // the null should be handled in the calling method
        if (matchResultMap.get(participant) == null
                || matchResultMap.get(participant).getGamesWon() == null) {
            return null;
        }
        
        MatchResultEnum matchResultEnum;
        int gamesWon = matchResultMap.get(participant).getGamesWon();
        if (matchResultMap.values()
                          .stream()
                          .filter((r) -> r.getForParticipant() != participant)
                          .anyMatch((r1) -> r1.getGamesWon() > gamesWon)) {
            matchResultEnum = MatchResultEnum.LOSS;
        } else if (matchResultMap
                .values()
                .stream()
                .filter((r) -> r.getForParticipant() != participant)
                .anyMatch((r1) -> r1.getGamesWon() == gamesWon)) {
            matchResultEnum = MatchResultEnum.TIE;
        } else {
            matchResultEnum = MatchResultEnum.WIN;
        }
        return matchResultEnum;
    }
    
    public List<Participant> getParticipants() {
        log.debug("in getParticipants: {}", matchResultMap.keySet());
        return new ArrayList<>(matchResultMap.keySet());
    }
    
    public boolean isBye() {
        return isBye;
    }

//
//    public int getWinsForParticipant(Participant participant) {
//        if (!matchResultsForParticipant.containsKey(participant)) {
//            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
//            return 0;
//        }
//
//        if (matchResultsForParticipant.get(participant) == null
//                || matchResultsForParticipant.get(participant).getGamesWon() == null) {
//            log.info("Participant does not yet have a submitted result: {}", participant);
//            return 0;
//        }
//
//        return matchResultsForParticipant.get(participant).getGamesWon();
//    }
    
    public void addMatchResult(MatchResult matchResult) {
        matchResultMap.put(matchResult.getForParticipant(), matchResult);
    }
    
    public void addParticipant(Participant participant) {
        if (!matchResultMap.containsKey(participant)) {
            MatchResult matchResult = new MatchResult(this, participant, null);
            this.matchResultMap.put(participant, matchResult);
        }
    }
    
    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                '}';
    }
}
