package com.romco.bracketeer.service;

import com.romco.bracketeer.model.MockDataModel;
import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.participant.Player;
import com.romco.bracketeer.model.tournament.Tournament;
import com.romco.bracketeer.model.tournament.TournamentImpl;
import com.romco.bracketeer.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TournamentServiceImpl implements TournamentService {
    
    // == fields
    MockDataModel mockDataModel = new MockDataModel();
    Tournament tournament;
    
    // == constructors
    
    @Autowired
    public TournamentServiceImpl() {
    }
    
    // == methods
    @Override
    public int createNewTournament() {
        this.tournament = new TournamentImpl(TournamentFormat.SWISS);
        return tournament.getId();
    }
    
    @Override
    public Tournament getTournament() {
        return tournament;
    }

    public Tournament getTournamentByCode(int code) {
        log.info("Getting tournament by code: {}", code);
        tournament = mockDataModel.getByCode(code);
        return tournament;
    }

    @Override
    public int saveTournament() {
        return tournament.getId();
    }

    @Override
    public Participant addPlayer(String playerName) {
        if (this.tournament == null) {
            createNewTournament();
        }
        
        Participant participant = new Player(playerName);
        if (tournament.addParticipant(participant)) {
            return participant;
        } else {
            return participant;
        }
        
    }
    
    @Override
    public List<Participant> getParticipants() {
        if (tournament == null) {
            return new ArrayList<>();
        }
        return tournament.getParticipants();
    }
    
    @Override
    public String removePlayer(String id) {
        int intId = Integer.parseInt(id);
        if (tournament.removeParticipant(intId)) {
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
