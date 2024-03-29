package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
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
        participant1.addPlayedMatch(this);
        isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this();
        addParticipant(participant1);
        participant1.addPlayedMatch(this);
        addParticipant(participant2);
        participant2.addPlayedMatch(this);
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
                             .filter(p -> !p.equals(participant))
                             // TODO remove peek after debugging
                             .peek(p -> log.trace("After filter: " + p))
                             .collect(Collectors.toList());
    }
    
    public Participant getOther(Participant participant) {
        List<Participant> others = matchResultMap.keySet().stream()
                                                 .filter(p -> !p.equals(participant))
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
        
        var matchResult = matchResultMap.get(participant);
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
        var otherParticipant = getOther(participant);

        var matchResult = matchResultMap.get(participant);
        matchResult.setGamesWon(gamesWon);
        var otherMatchResult = matchResultMap.get(otherParticipant);
        otherMatchResult.setGamesWon(gamesLost);
        log.debug("In setMatchScore, creating matchResults: {} and {}", matchResult, otherMatchResult);
        
        List<MatchResult> matchResults = new ArrayList<>();
        // the order of the elements should be kept so that
        // the first result is the one with input participant
        matchResults.add(matchResult);
        matchResults.add(otherMatchResult);
        return matchResults;
    }

    // TODO check if it can just be a boolean instead of a map
    public List<MatchResult> setMatchScore(Map<Participant, Integer> gamesWonByParticipants) {
        if (!matchResultMap.keySet().containsAll(gamesWonByParticipants.keySet())) {
            log.warn("Match doesn't contain all provided participants. Match participants: {}, provided: {}",
                                                  matchResultMap.keySet(),
                                                  gamesWonByParticipants.keySet());
            return Collections.emptyList();
        }

        List<MatchResult> resultList = new ArrayList<>();
        for (Map.Entry<Participant, Integer> participantGamesWonEntry : gamesWonByParticipants.entrySet()) {
            var matchResult = matchResultMap.get(participantGamesWonEntry.getKey());
            matchResult.setGamesWon(participantGamesWonEntry.getValue());
            resultList.add(matchResult);
        }
        return resultList;
    }
    
    public MatchResultEnum getMatchResult(Participant participant) {
        if (!matchResultMap.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return MatchResultEnum.NOT_PLAYED;
        }
        // if the other entry is null, this means that no result is present yet
        // the null should be handled in the calling method
        if (matchResultMap.get(participant) == null
                || matchResultMap.get(participant).getGamesWon() == null) {
            return MatchResultEnum.NOT_PLAYED;
        }
        
        MatchResultEnum matchResultEnum;
        int gamesWon = matchResultMap.get(participant).getGamesWon();
        if (matchResultMap.values()
                          .stream()
                          .filter(r -> r.getForParticipant() != participant)
                          .anyMatch(r1 -> r1.getGamesWon() > gamesWon)) {
            matchResultEnum = MatchResultEnum.LOSS;
        } else if (matchResultMap
                .values()
                .stream()
                .filter(r -> r.getForParticipant() != participant)
                .anyMatch(r1 -> r1.getGamesWon() == gamesWon)) {
            matchResultEnum = MatchResultEnum.TIE;
        } else {
            matchResultEnum = MatchResultEnum.WIN;
        }
        return matchResultEnum;
    }
    
    public List<Participant> getParticipants() {
        return new ArrayList<>(matchResultMap.keySet());
    }
    
    public boolean isBye() {
        return isBye;
    }
    
    public void addMatchResult(MatchResult matchResult) {
        matchResultMap.put(matchResult.getForParticipant(), matchResult);
    }
    
    public void addParticipant(Participant participant) {
        matchResultMap.computeIfAbsent(participant, p -> {
            var matchResult = new MatchResult(this, p, null);
            // TODO recheck isBye here
            p.addPlayedMatch(this);
            return matchResult;
        });
    }
    
    @Override
    public String toString() {
        List<String> participantNames = new ArrayList<>();
        if (matchResultMap != null && !matchResultMap.isEmpty()) {
            matchResultMap.keySet().forEach(p -> participantNames.add(p.getName()));
        }
        return "Match{" +
                "id=" + id +
                ", participants (names)=" + participantNames +
                '}';
    }
}
