package com.romco.bracketeer.service;

import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.tournament.Tournament;

import java.util.List;

public interface MainService {
    void createNewTournament();
    Participant addPlayer(String name);
    String removePlayer(int id);
    void setResult(int roundId, int playerId, int gamesWon, int gamesLost);
    void generateNextRound();
}
