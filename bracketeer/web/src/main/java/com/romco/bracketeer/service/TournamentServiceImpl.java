package com.romco.bracketeer.service;

<<<<<<< HEAD
import com.romco.domain.MockDataModel;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
=======

>>>>>>> be88e0fe0ef736522434cea5e7abdfdce757de22
import com.romco.bracketeer.util.Message;
import com.romco.dao.TournamentDao;
import com.romco.daoimpl.TournamentDaoImpl;
import com.romco.domain.util.MockDataModel;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
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
    
    TournamentDao tournamentDao = new TournamentDaoImpl();
    
    Tournament tournament;
    
    // == constructors
    
    @Autowired
    public TournamentServiceImpl() {
    }
    
    // == methods
    @Override
    public String createNewTournament() {
        this.tournament = new TournamentImpl(TournamentFormat.SWISS);
        return tournament.getCode();
    }
    
    public TournamentDao getTournamentDao() {
        return tournamentDao;
    }
    
    @Override
    public Tournament getTournament() {
        return tournament;
    }

    public Tournament getTournamentByCode(String code) {
        log.info("Getting tournament by code: {}", code);
        tournament = mockDataModel.getByCode(Integer.parseInt(code));
        return tournament;
    }

    @Override
    public String saveTournament() {
        mockDataModel.addTournament(tournament);
        return tournament.getCode();
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
    
    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentDao.selectAll();
    }
}
