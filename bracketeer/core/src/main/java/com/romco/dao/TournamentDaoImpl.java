package com.romco.dao;

import com.romco.domain.tournament.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TournamentDaoImpl implements TournamentDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(Tournament tournament) {
        jdbcTemplate.execute("CREATE TABLE tournament(" +
                                     "id SERIAL, col1 VARCHAR(255)");

    }
}
