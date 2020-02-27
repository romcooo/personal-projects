package com.romco.domain.tournament;

import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;

import java.util.List;

public interface Tournament {
    long getId();
    void setId(long id);
    
    String getCode();
    void setCode(String code);
    
    String getName();
    void setName(String name);
    
    TournamentFormat getType();
    void setType(TournamentFormat type);
    
    boolean addParticipant(Participant participant);
    Participant removeParticipant(long id);
    
    List<Participant> getParticipants();
    
    Round getRound(int n);
    
    Round generateNextRound();

    boolean setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    boolean setMatchResult(int roundNumber, int participantId, int gamesWon, int gamesLost);
    void setStartingScore(Participant participant, double by);
}
