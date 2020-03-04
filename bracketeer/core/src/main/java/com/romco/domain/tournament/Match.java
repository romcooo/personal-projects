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
    public static final String PARTICIPANT_NOT_FOUND_WARN_MESSAGE
            = "Match doesn't contain the provided participant: {}";

    private long id;
    private int matchNumber;
    private List<Participant> participants;
    private int bestOf;
    private boolean isBye;
    private Map<Participant, MatchResult> matchResultsForParticipant;
    private Round ofRound;

    public Match() {
        participants = new ArrayList<>();
        matchResultsForParticipant = new HashMap<>();
    }

    public Match(Participant participant1) {
        this();
        
        participants.add(participant1);

        MatchResult matchResult1 = new MatchResult(this, participant1, 0);
        matchResultsForParticipant.put(participant1, matchResult1);
        
        isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this();
    
        participants.add(participant1);
        participants.add(participant2);

        MatchResult matchResult1 = new MatchResult(this, participant1, 0);
        MatchResult matchResult2 = new MatchResult(this, participant2, 0);

        matchResultsForParticipant.put(participant1, matchResult1);
        matchResultsForParticipant.put(participant2, matchResult2);

//        log.info("In Match constructor, match: {}", getMatchResultsForParticipant().get(participant1).getGamesWon());

        isBye = false;
    }

    public boolean setWinsByParticipantCode(String participantCode, int gamesWon) {
        for (Participant participant : participants) {
            if (participant.getCode().equals(participantCode)) {
                matchResultsForParticipant.get(participant).setGamesWon(gamesWon);
                return true;
            }
        }
        log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participantCode);
        return false;
    }

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

    public Map<Participant, MatchResult> getMatchResultsForParticipant() {
        return matchResultsForParticipant;
    }

    public List<Participant> getOthers(Participant participant) {
        return participants.stream()
                    .filter((p) -> !p.equals(participant))
                    .peek((p) -> log.debug("After filter: " + p))
                    .collect(Collectors.toList());
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

    public boolean setMatchScore(Participant participant, int gamesWon) {
        if (!participants.contains(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return false;
        }
        MatchResult matchResult = new MatchResult(this, participant, gamesWon);
        matchResultsForParticipant.put(participant, matchResult);
        return true;
    }

    public boolean setMatchScore(Participant participant, int gamesWon, int gamesLost) {
        // if participant is not of this match
        if (!participants.contains(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
            return false;
        }
        MatchResult matchResult = new MatchResult(this, participant, gamesWon);
        MatchResult other = new MatchResult(this, getOther(participant), gamesLost);
        matchResultsForParticipant.put(participant, matchResult);
        matchResultsForParticipant.put(getOther(participant), other);
        return true;
    }
    
    public MatchResultEnum getMatchResult(Participant participant) {
        if (!matchResultsForParticipant.containsKey(participant)) {
            log.warn(PARTICIPANT_NOT_FOUND_WARN_MESSAGE, participant);
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
        return new ArrayList<>(participants);
    }
    
    public boolean isBye() {
        return isBye;
    }
}
