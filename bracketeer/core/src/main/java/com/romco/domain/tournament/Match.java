package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
public class Match {
    public static final String PARTICIPANT_NOT_FOUND_WARN_MESSAGE
            = "Match doesn't contain the provided participant: {}";

    private long id;
    private int matchNumber;
//    private List<Participant> participants;
    private boolean isBye;
    private Map<Participant, MatchResult> matchResultsForParticipant;
    private List<MatchResult> matchResults;
    private Round ofRound;

    public Match() {
//        participants = new ArrayList<>();
        matchResultsForParticipant = new HashMap<>();
    }

    public Match(Participant participant1) {
        this();
        matchResultsForParticipant.put(participant1, null);
//        participants.add(participant1);

//        MatchResult matchResult1 = new MatchResult(this, participant1, 0);
//        matchResultsForParticipant.put(participant1, matchResult1);
    
        isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this();

        matchResultsForParticipant.put(participant1, null);
        matchResultsForParticipant.put(participant2, null);

//        participants.add(participant1);
//        participants.add(participant2);

//        MatchResult matchResult1 = new MatchResult(this, participant1, 0);
//        MatchResult matchResult2 = new MatchResult(this, participant2, 0);
//
//        matchResultsForParticipant.put(participant1, matchResult1);
//        matchResultsForParticipant.put(participant2, matchResult2);

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

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
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

    public Map<Participant, MatchResult> getMatchResultsForParticipant() {
        return matchResultsForParticipant;
    }

    public List<Participant> getOthers(Participant participant) {
        return matchResultsForParticipant.keySet().stream()
                    .filter((p) -> !p.equals(participant))
                    .peek((p) -> log.debug("After filter: " + p))
                    .collect(Collectors.toList());
    }
    
    public Participant getOther(Participant participant) {
        List<Participant> others = matchResultsForParticipant.keySet().stream()
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
        
        if (!matchResultsForParticipant.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return Collections.emptyList();
        }
        MatchResult matchResult = new MatchResult(this, participant, gamesWon);
        matchResultsForParticipant.put(participant, matchResult);
    
        List<MatchResult> matchResults = new ArrayList<>();
        return matchResults;
    }

    public List<MatchResult> setMatchScore(Participant participant, int gamesWon, int gamesLost) {
        // if participant is not of this match
        if (!matchResultsForParticipant.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return Collections.emptyList();
        }
        Participant otherParticipant = getOther(participant);
        MatchResult matchResult = new MatchResult(this, participant, gamesWon);
        MatchResult otherMatchResult = new MatchResult(this, otherParticipant, gamesLost);
        log.debug("In setMatchScore, creating matchResults: {} and {}", matchResult, otherMatchResult);
        matchResultsForParticipant.put(participant, matchResult);
        matchResultsForParticipant.put(otherParticipant, otherMatchResult);
        List<MatchResult> matchResults = new ArrayList<>();
        matchResults.add(matchResult);
        matchResults.add(otherMatchResult);
        return matchResults;
    }
    
    public MatchResultEnum getMatchResult(Participant participant) {
        if (!matchResultsForParticipant.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return null;
        }
        // if the other entry is null, this means that no result is present yet
        // the null should be handled in the calling method
        if (matchResultsForParticipant.get(participant) == null) {
            return null;
        }
        MatchResultEnum matchResultEnum;
        int gamesWon = matchResultsForParticipant.get(participant).getGamesWon();
        if (matchResultsForParticipant.values()
                                      .stream()
                                      .filter((r) -> r.getForParticipant() != participant)
                                      .anyMatch((r1) -> r1.getGamesWon() > gamesWon)) {
            matchResultEnum = MatchResultEnum.LOSS;
        } else if (matchResultsForParticipant
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
        log.debug("in getParticipants: {}", matchResultsForParticipant.keySet());
        return new ArrayList<>(matchResultsForParticipant.keySet());
    }
    
    public boolean isBye() {
        return isBye;
    }
    
    public int getWinsForParticipant(Participant participant) {
//        if (!participants.contains(participant)) {
//            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
//            return 0;
//        }
        
        if (!matchResultsForParticipant.containsKey(participant) || matchResultsForParticipant.get(participant) == null) {
            log.info("Participant does not yet have a submitted result: {}", participant);
            return 0;
        }
        
        return matchResultsForParticipant.get(participant).getGamesWon();
    }
    
    public void addMatchResult(MatchResult matchResult) {
        matchResultsForParticipant.put(matchResult.getForParticipant(), matchResult);
    }
    
    public void addParticipant(Participant participant) {
        this.matchResultsForParticipant.put(participant, null);
    }
    
    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                '}';
    }
}
