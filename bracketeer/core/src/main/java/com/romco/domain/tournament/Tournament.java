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
    void setParticipants(List<Participant> participants);


    List<Participant> getParticipants();
    List<Participant> getParticipantsForAfterRound(int roundNumber);
    
    Round getRound(int roundNumber);
    List<Round> getRounds();

    void setRounds(List<Round> rounds);
    
    Round generateNextRound();
    Round generateRound(int n);

    List<MatchResult> setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    List<MatchResult> setMatchResult(int roundNumber, String participantCode, int gamesWon, int gamesLost);
    List<MatchResult> setMatchResult(int roundNumber, String participantCode, int gamesWon);

    void setStartingScore(Participant participant, double by);
}
