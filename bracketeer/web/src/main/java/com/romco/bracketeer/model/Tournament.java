package com.romco.bracketeer.model;

import com.romco.bracketeer.model.participant.Participant;

import java.util.List;

public interface Tournament {
    boolean addParticipant(Participant participant);
    boolean removeParticipant(Participant participant);
    
    Round generateNextRound();
    Round getRound(int n);
    boolean setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    void printStandings();
}
