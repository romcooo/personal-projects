package com.romco.bracketeer.service;

import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.participant.Player;
import com.romco.bracketeer.model.tournament.Tournament;
import com.romco.bracketeer.model.tournament.TournamentImpl;
import com.romco.bracketeer.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainServiceImpl implements MainService {

    // == fields
    Tournament tournament;

    // == constructors
    @Autowired
    public MainServiceImpl() {
    }

    // == methods
    
    @Override
    public void createNewTournament() {
        this.tournament = new TournamentImpl(TournamentFormat.SWISS);
    }
    
    @Override
    public Participant addPlayer(String name) {
        Participant participant = new Player(name);
        if (tournament.addParticipant(participant)) {
            return participant;
        } else {
            return participant;
        }
        
    }
    
    @Override
    public String removePlayer(int id) {
        if (tournament.removeParticipant(id)) {
            return Message.PLAYER_REMOVED;
        } else {
            return Message.PLAYER_DOESNT_EXIST;
        }
    }
    
    @Override
    public void setResult(int roundId, int playerId, int gamesWon, int gamesLost) {
        tournament.setMatchResult(roundId, playerId, gamesWon, gamesLost);
    }
    
    @Override
    public void generateNextRound() {
        tournament.generateNextRound();
    }
    
    
}
