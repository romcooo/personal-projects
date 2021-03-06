package com.romco.bracketeer.persistence.rowmapper;

import com.romco.bracketeer.domain.tournament.Round;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundRowMapper implements RowMapper<Round> {
    @Override
    public Round mapRow(ResultSet rs, int rowNum) throws SQLException {
        Round round = new Round();
        round.setId(rs.getLong("id"));
        round.setBestOf(rs.getInt("best_of"));
        round.setRoundNumber(rs.getInt("round_number"));
        return round;
    }
}
