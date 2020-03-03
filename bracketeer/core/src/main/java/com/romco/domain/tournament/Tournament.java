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
    List<Participant> getParticipantsForAfterRound(int roundNumber);
    
    Round getRound(int n);
    List<Round> getRounds();

    Round generateNextRound();
    Round generateRound(int n);

    boolean setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    boolean setMatchResult(int roundNumber, String participantCode, int gamesWon, int gamesLost);
    void setStartingScore(Participant participant, double by);
}
