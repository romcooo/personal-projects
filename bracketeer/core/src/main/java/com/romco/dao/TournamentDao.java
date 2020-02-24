package com.romco.dao;

import com.romco.domain.tournament.Tournament;

import javax.sql.DataSource;
import java.util.List;

public interface TournamentDao {

    // Set the data-source that will be required to create a connection to the database
    void setDataSource(DataSource ds);

    // Retrieve a tournament by id
    Tournament getTournament(Integer id);

    // Retrieve all the tournaments from the table;
    List<Tournament> getAllTournaments();

    // Create a record in the tournament table
    boolean create(Tournament tournament);
    // Delete passed tournament if exists
    boolean delete(Tournament tournament);
    // Update the tournament by id of passed tournament
    boolean update(Tournament tournament);

    void cleanup();
}
