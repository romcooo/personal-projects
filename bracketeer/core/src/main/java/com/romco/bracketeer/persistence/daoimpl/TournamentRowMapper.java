package com.romco.bracketeer.persistence.daoimpl;

import com.romco.bracketeer.domain.matcher.TournamentFormat;
import com.romco.bracketeer.domain.tournament.Tournament;
import com.romco.bracketeer.domain.tournament.TournamentImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TournamentRowMapper implements RowMapper<Tournament> {
    @Override
    public Tournament mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tournament tournament = new TournamentImpl(
                TournamentFormat.valueOf(rs.getString("type")));
        
        tournament.setId(rs.getLong("id"));
        tournament.setCode(rs.getString("code"));
        tournament.setName(rs.getString("name"));
        
        return tournament;
    }
}
