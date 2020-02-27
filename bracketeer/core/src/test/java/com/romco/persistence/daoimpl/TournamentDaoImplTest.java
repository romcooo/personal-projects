package com.romco.persistence.daoimpl;

import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.tournament.TournamentImpl;
import com.romco.persistence.dao.TournamentDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TournamentDaoImplTest {
    
    @Autowired
    TournamentDao tournamentDao;
    
//    @Test
//    void setDataSource() {
//    }
    
    @Test
    void create() {
        assertTrue(tournamentDao.create(new TournamentImpl("Test1",
                                                           "test tournament 1",
                                                           TournamentFormat.SWISS)) > -1);
    }
    
    @Test
    void retrieve() {
    
    }
    
    @Test
    void testRetrieve() {
    }
    
    @Test
    void retrieveAll() {
    }
    
    @Test
    void delete() {
    }
    
    @Test
    void update() {
    }
    
    @Test
    void cleanup() {
    }
}