package com.romco.bracketeer.persistence.daoimpl;

import com.romco.bracketeer.domain.tournament.MatchResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchResultRowMapper implements RowMapper<MatchResult> {
    @Override
    public MatchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchResult matchResult = new MatchResult();
    
//        matchResult.setForParticipant(rs.getLong("participant_id"));
//        matchResult.setOfMatch(rs.getLong("match_id"));
        matchResult.setGamesWon(rs.getInt("games_won"));
//        Map.Entry<MatchResult, Long> entry = new AbstractMap.SimpleEntry<>(matchResult,
//                                                                           rs.getLong("participant_id"));
        
//        return entry;
        return matchResult;
    }
}
