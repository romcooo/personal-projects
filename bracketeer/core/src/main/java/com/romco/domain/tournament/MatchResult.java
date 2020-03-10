package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MatchResult {
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
    
    @Override
    public String toString() {
        return "MatchResult{" +
                "ofMatch=" + ofMatch +
                ", forParticipant=" + forParticipant +
                ", gamesWon=" + gamesWon +
                '}';
    }
}
