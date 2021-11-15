package com.romco.bracketeer.service;

import com.romco.bracketeer.util.Message;
import com.romco.bracketeer.domain.matcher.TournamentFormat;
import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.participant.Player;
import com.romco.bracketeer.domain.tournament.*;
import com.romco.bracketeer.persistence.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TournamentServiceImpl implements TournamentService {
    public static final String DEFAULT_TOURNAMENT_NAME = "Tournament Name";
    
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
    @Autowired
    RuleSetDao ruleSetDao;
    
    // == constructors
    @Autowired
    public TournamentServiceImpl() {
        // autowired empty constructor
    }
    
    // == methods
    @Override
    public String createNewTournament() {
        this.tournament = new TournamentImpl(TournamentFormat.SWISS, DEFAULT_TOURNAMENT_NAME);
        return tournament.getCode();
    }
    
    @Override
    public Tournament getTournament() {
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

    /**
     * If the code matches the current tournament, does nothing but return the current tournament.
     * Otherwise, looks up a tournament based on the code in database and returns it.
     * @param code - code of the tournament
     * @return the tournament with the given code if it exists, otherwise returns null
     */
    @Override
    public Tournament getTournamentByCode(String code) {
        log.info("in getTournamentByCode, code: {}", code);

        // if current tournament already is the one requested
        if (tournament != null && tournament.getCode().equals(code)) {
            return tournament;
        }

        // else look it up
        tournament = tournamentDao.retrieve(code);
        if (tournament != null) {
            // get ruleSet
            // TODO - check dao - type ("SWISS") needs to be moved to ruleSet in DB and properly retrieved
            RuleSet ruleSet = ruleSetDao.retrieveByTournamentId(tournament.getId());
            tournament.setRuleSet(ruleSet);

            // get participants and add them to the tournament
            List<Participant> participants = participantDao.retrieveByTournamentId(tournament.getId());
            for (Participant participant : participants) {
                participant.setOfTournament(tournament);
                tournament.addParticipant(participant);
            }
//            tournament.setParticipants(participants);
            
            // get rounds and add them to the tournament
            List<Round> rounds = roundDao.retrieveByTournamentId(tournament.getId());
            for (Round round : rounds) {
                round.setOfTournament(tournament);
                // TODO there is a bug somewhere here
                //  -> if you re-open a tournament, you can't open the last round pairings
                //get matches and add them to the round
                List<Match> matches = matchDao.retrieveByRoundId(round.getId());
                for (Match match : matches) {
                    match.setOfRound(round);
                    
                    Map<MatchResult, Long> matchResultMap = matchResultDao.retrieveByMatchId(match.getId());
                    for (MatchResult matchResult : matchResultMap.keySet()) {
                        Participant ofMatch = participants.stream()
                                                          .filter(participant -> participant.getId() == matchResultMap.get(matchResult))
                                                          .findAny()
                                                          .get();
                        matchResult.setOfMatch(match);
                        matchResult.setForParticipant(ofMatch);
                        match.addMatchResult(matchResult);
                        log.debug("MatchResult: {}, map:{}, participants: {}", matchResult, matchResultMap, participants);
                        log.debug("Match: {}", match);
                    }

                    
                }
                round.setMatches(matches);
                
            }
            tournament.setRounds(rounds);
            
        }
        return tournament;
    }
    
    @Override
    public void setTournamentName(String tournamentName) {
        log.info("In setTournamentName, tournamentName = {}", tournamentName);
        tournament.setName(tournamentName);
    }

    @Override
    public void setTournamentType(String tournamentType) {
        log.info(("in setTournamentType, tournamentType = {}"), tournamentType);
        tournament.getRuleSet().setType(TournamentFormat.valueOf(tournamentType));
    }

    @Override
    public List<Participant> getParticipants() {
        if (tournament == null) {
            return new ArrayList<>();
        }
        return tournament.getParticipants();
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
    
//    @Override
//    public void setResult(int roundNumber, String participantCode, int gamesWon, int gamesLost) {
//        List<MatchResult> matchResults = tournament.setMatchResult(roundNumber, participantCode, gamesWon, gamesLost);
//        if (!matchResults.isEmpty()){
//            for (MatchResult matchResult : matchResults) {
//                log.debug("in setResult, creating matchResult {}", matchResult);
//                matchResultDao.update(matchResult);
//            }
//        }
//    }

    public void setResult(int roundNumber, int matchNumber, Map<String, Integer> participantCodeToGamesWon) {

        List<MatchResult> matchResults = tournament.setMatchResult(roundNumber, matchNumber, participantCodeToGamesWon);
        if (!matchResults.isEmpty()) {
            for (MatchResult matchResult : matchResults) {
                log.debug("in setResult, creating matchResult: {}", matchResult);
                matchResultDao.update(matchResult);
            }
        }
    }
    
    @Override
    public void generateRound(int n) {
        Round round = tournament.generateRound(n);
        round.setId(roundDao.create(round));
        log.debug("Generated and stored round: {}", round);
        for (Match match : round.getMatches()) {
            match.setId(matchDao.create(match));
            
            for (Participant participant : match.getMatchResultMap().keySet()) {
                MatchResult matchResult = match.getMatchResultMap().get(participant);
                matchResultDao.create(matchResult);
                log.debug("Stored match result: {}", matchResult);
            }
            
            log.debug("Stored match: {}", match);
        }
    }

    @Override
    public int getMaxNumberOfRounds() {
        return tournament.getMaxNumberOfRounds();
    }

    @Override
    public boolean setPointsForMatchResultType(MatchResultEnum matchResultType, double pointsForMatchResultType) {
        // TODO store this ruleSet!
        return tournament.getRuleSet().setPointsForResult(matchResultType, pointsForMatchResultType);
    }

    @Override
    public boolean resetPointsForMatchResultTypesToDefault() {
        // TODO store to DB and if storing fails, return false
        tournament.getRuleSet().setPointsForResult(MatchResultEnum.WIN, RuleSet.DEFAULT_POINTS_FOR_WIN);
        tournament.getRuleSet().setPointsForResult(MatchResultEnum.LOSS, RuleSet.DEFAULT_POINTS_FOR_LOSS);
        tournament.getRuleSet().setPointsForResult(MatchResultEnum.TIE, RuleSet.DEFAULT_POINTS_FOR_TIE);
        return true;
    }

    @Override
    public Collection<Tournament> getAllTournaments() {
        return tournamentDao.retrieveAll();
    }
}
