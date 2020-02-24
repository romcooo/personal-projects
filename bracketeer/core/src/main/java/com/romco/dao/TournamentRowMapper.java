package com.romco.dao;

import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TournamentRowMapper implements RowMapper<Tournament> {
    @Override
    public Tournament mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        return tournament;
    }
}
