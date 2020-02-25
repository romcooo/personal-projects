package com.romco.dao;

import com.romco.domain.tournament.Tournament;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

public interface TournamentDao {
    
    // set the data-source required to create a connection to the database
    void setDataSource(DataSource dataSource);
    
    // "insert into" - create a record in the tournament table
    boolean insert(Tournament tournament);
    
    // retrieve a single tournament from the table
    Tournament select(long id);
    Tournament select(String code);
    
    // retrieve all tournaments from the table
    List<Tournament> selectAll();
    
    // delete a tournament
    boolean delete(Tournament tournament);
    
    // update an existing tournament (based on id)
    boolean update(Tournament tournament);
    
    // for test purposes
    void cleanup();
}
