package com.romco.bracketeer.service;

import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.tournament.Tournament;

import java.util.List;

public interface TournamentService {
    int createNewTournament();
    Tournament getTournament();
    String saveTournament();
    Tournament getTournamentByCode(String code);
    List<Participant> getParticipants();
    Participant addPlayer(String name);
    String removePlayer(String id);
    void setResult(int roundId, int playerId, int gamesWon, int gamesLost);
    void generateNextRound();
}
