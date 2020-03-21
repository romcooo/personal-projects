package com.romco.bracketeer.service;


import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Tournament;

import java.util.Collection;
import java.util.List;

public interface TournamentService {
    
    String createNewTournament();
    Tournament getTournament();
    String saveTournament();
    Tournament getTournamentByCode(String code);

    void setTournamentName(String tournamentName);
    void setTournamentType(String tournamentType);

    Collection<Tournament> getAllTournaments();

    List<Participant> getParticipants();
    Participant addPlayer(String name);
    String removePlayer(String id);

    void generateRound(int n);
    int getMaxNumberOfRounds();

    void setResult(int roundNumber, String participantCode, int gamesWon, int gamesLost);
}
