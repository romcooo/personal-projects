package com.romco.bracketeer.service;


import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.MatchResultEnum;
import com.romco.bracketeer.domain.tournament.Tournament;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    boolean setPointsForMatchResultType(MatchResultEnum matchResultType, double pointsForMatchResultType);

    void setResult(int roundNumber, int matchNumber, Map<String, Integer> participantCodeToGamesWon);
}
