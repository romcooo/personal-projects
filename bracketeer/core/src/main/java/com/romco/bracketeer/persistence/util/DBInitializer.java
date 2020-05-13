package com.romco.bracketeer.persistence.util;

import com.romco.bracketeer.persistence.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBInitializer {
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
    
    @PostConstruct
    public void initialize() {
        matchResultDao.cleanup();
        matchDao.cleanup();
        roundDao.cleanup();
        participantDao.cleanup();
        tournamentDao.cleanup();
    }
    
//    @PostConstruct
//    public void createSeedData() {
//        Tournament t1 = new TournamentImpl(-1,
//                                           "test1",
//                                           "testTournament1",
//                                           null,
//                                           null,
//                                           TournamentFormat.SWISS,
//                                           RuleSet.getDefaultRuleSet());
//        TournamentDaoImpl dao = new TournamentDaoImpl();
//        dao.insert(t1);
//    }
    
}
