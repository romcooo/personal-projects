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
    List<Participant> getParticipants();
    Participant addPlayer(String name);
    String removePlayer(String id);
    void setResult(int roundId, int playerId, int gamesWon, int gamesLost);
    void generateRound(int n);
    
    Collection<Tournament> getAllTournaments();
}
