package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.MatchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MatchResultMapResultSetExtractor implements ResultSetExtractor<Map<MatchResult, Long>> {
    @Override
    public Map<MatchResult, Long> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<MatchResult, Long> resultMap = new HashMap<>();
        while (rs.next()) {
            MatchResult matchResult = new MatchResult();
            matchResult.setGamesWon(rs.getInt("games_won"));
            resultMap.put(matchResult, rs.getLong("participant_id"));
        }
        log.debug("In retrieveByMatchId.extractData, resultMap: {}, resultSet: {}", resultMap, rs);
        return resultMap;
    }
}
