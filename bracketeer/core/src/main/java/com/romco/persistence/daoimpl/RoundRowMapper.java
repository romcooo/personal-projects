package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Round;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundRowMapper implements RowMapper<Round> {
    @Override
    public Round mapRow(ResultSet rs, int rowNum) throws SQLException {
        Round round = new Round();
        round.setId(rs.getLong("id"));
        round.setBestOf(rs.getInt("best_of"));
        return round;
    }
}
