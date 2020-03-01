package com.romco.persistence.util;

import com.romco.persistence.dao.ParticipantDao;
import com.romco.persistence.dao.RoundDao;
import com.romco.persistence.dao.TournamentDao;
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
    
    @PostConstruct
    public void initialize() {
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
