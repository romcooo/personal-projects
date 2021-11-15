package com.romco.bracketeer.persistence.rowmapper;

import com.romco.bracketeer.domain.tournament.RuleSet;
import com.romco.bracketeer.persistence.daoimpl.RuleSetDaoImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleSetRowMapper implements RowMapper<RuleSet> {

    @Override
    public RuleSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        RuleSet ruleSet = new RuleSet.RuleSetBuilder()
                .withPointsForWin(rs.getDouble(RuleSetDaoImpl.COL_POINTS_FOR_WIN))
                .withPointsForLoss(rs.getDouble(RuleSetDaoImpl.COL_POINTS_FOR_LOSS))
                // TODO .withType()
                .withPointsForLoss(rs.getDouble(RuleSetDaoImpl.COL_POINTS_FOR_LOSS))
                .withPointsForLoss(rs.getDouble(RuleSetDaoImpl.COL_POINTS_FOR_LOSS))
                .build();
        return ruleSet;
    }
}
