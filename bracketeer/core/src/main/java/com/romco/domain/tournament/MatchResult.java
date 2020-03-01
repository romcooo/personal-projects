package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class MatchResult {
    private long id;
    private Match ofMatch;
    private Participant forParticipant;
    private int gamesWon;
    private int gamesLost;
    private MatchResultEnum result;
    
    public MatchResult() {
    }
    
    public MatchResult(Match ofMatch, Participant forParticipant, int gamesWon, int gamesLost) {
        this.ofMatch = ofMatch;
        this.forParticipant = forParticipant;
        this.setResult(gamesWon, gamesLost);
    }
    
    public void setResult(int gamesWon, int gamesLost) {
        setGamesWon(gamesWon);
        setGamesLost(gamesLost);
        if (gamesWon > gamesLost) {
            setResult(MatchResultEnum.WIN);
        } else if (gamesWon < gamesLost) {
            setResult(MatchResultEnum.LOSS);
        } else {
            setResult(MatchResultEnum.TIE);
        }
    }
}
