package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class MatchResult {
//    private long id;
    private Match ofMatch;
    private Participant forParticipant;
    private Integer gamesWon;
    
    public MatchResult() {
    }
    
    public MatchResult(Match ofMatch, Participant forParticipant, Integer gamesWon) {
        this.ofMatch = ofMatch;
        this.forParticipant = forParticipant;
        this.gamesWon = gamesWon;
    }

    public Integer getGamesWon() {
        log.debug("In getGamesWon for matchResult {}", this);
        return gamesWon;
    }
    
    @Override
    public String toString() {
        return "MatchResult{" +
                "ofMatch=" + ofMatch +
                ", forParticipant=" + forParticipant +
                ", gamesWon=" + gamesWon +
                '}';
    }
}
