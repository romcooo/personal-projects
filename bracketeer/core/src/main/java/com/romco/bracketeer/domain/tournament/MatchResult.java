package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MatchResult {
    private Match ofMatch;
    private Participant forParticipant;
    private Integer gamesWon;

    //TODO this can be done better surely
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

    // this has to compare strictly instance, because of how it is handled in the DAO (two matchResults
    // with the same values are not in fact the same and must be treated as 2 separate entities)
    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
