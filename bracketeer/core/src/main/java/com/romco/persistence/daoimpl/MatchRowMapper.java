package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Match;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MatchRowMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
        Match match = new Match();
        
        match.setId(rs.getLong("id"));
        match.setBye(rs.getBoolean("is_bye"));
    
        return match;
    }
}
