package com.romco.bracketeer.service;

import com.romco.bracketeer.util.Message;
import com.romco.domain.tournament.Match;
import com.romco.domain.tournament.Round;
import com.romco.persistence.dao.*;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
import com.romco.domain.util.MockDataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class TournamentServiceImpl implements TournamentService {
    
    // == fields
    Tournament tournament;
    
    // == DAO
    @Autowired
    TournamentDao tournamentDao;
    @Autowired
    ParticipantDao participantDao;
    @Autowired
    RoundDao roundDao;
    @Autowired
    MatchDao matchDao;
    @Autowired
    MatchResultDao matchResultDao;
    
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
    
    @Override
    public Tournament getTournament() {
        return tournament;
    }

    public Tournament getTournamentByCode(String code) {
        log.info("Getting tournament by code: {}", code);
        tournament = tournamentDao.retrieve(code);
        if (tournament != null) {
            // get participants and add them to the tournament
            List<Participant> participants = participantDao.retrieveByTournamentId(tournament.getId());
            for (Participant participant : participants) {
                participant.setOfTournament(tournament);
            }
            tournament.setParticipants(participants);
            
            // get rounds and add them to the tournament
            List<Round> rounds = roundDao.retrieveByTournamentId(tournament.getId());
            for (Round round : rounds) {
                round.setOfTournament(tournament);
                
                //get matches and add them to the round
                List<Match> matches = matchDao.retrieveByRoundId(round.getId());
                for (Match match : matches) {
                    match.setOfRound(round);
                }
                round.setMatches(matches);
                
            }
            tournament.setRounds(rounds);
            
        }
        return tournament;
    }

    @Override
    public String saveTournament() {
        if (tournamentDao.retrieve(tournament.getId()) == null) {
//            tournament.setId(tournamentDao.create(tournament)); - id is set in the dao
            tournamentDao.create(tournament);
        } else {
            tournamentDao.update(tournament);
        }
        for (Participant participant : tournament.getParticipants()) {
            log.debug("participant: {}, ofTournament: {}",
                      participant.toString(),
                      participant.getOfTournament());
            if (participantDao.retrieve(participant.getId()) == null) {
                participantDao.create(participant);
            } else {
                participantDao.update(participant);
            }
        }
        return tournament.getCode();
    }

    @Override
    public Participant addPlayer(String playerName) {
        if (this.tournament == null) {
            createNewTournament();
        }
        Participant participant = new Player(playerName);
        tournament.addParticipant(participant);
        return participant;
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
        long intId = Long.parseLong(id);
        Participant participant = tournament.removeParticipant(intId);
        if (participant != null) {
            participantDao.delete(participant);
            return Message.PLAYER_REMOVED;
        } else {
            return Message.PLAYER_DOESNT_EXIST;
        }
    }
    
    @Override
    public void setResult(int roundNumber, String participantCode, int gamesWon, int gamesLost) {
        tournament.setMatchResult(roundNumber, participantCode, gamesWon, gamesLost);
        // TODO persistence
        
    }
    
    @Override
    public void generateRound(int n) {
        Round round = tournament.generateRound(n);
        roundDao.create(round);
        log.debug("Generated and stored round: {}", round);
    }
    
    @Override
    public Collection<Tournament> getAllTournaments() {
        return tournamentDao.retrieveAll();
    }
}
