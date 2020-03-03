package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class MatchResult {
    private long id;
    private Match ofMatch;
    private Participant forParticipant;
    private int gamesWon;
    
    public MatchResult(Match ofMatch, Participant forParticipant, int gamesWon) {
        this.ofMatch = ofMatch;
        this.forParticipant = forParticipant;
        this.gamesWon = gamesWon;
    }

    public int getGamesWon() {
        log.debug("In getGamesWon");
        return gamesWon;
    }

}
