package com.romco.persistence.daoimpl;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.MatchResult;
import com.romco.persistence.dao.MatchResultDao;
import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MatchResultDaoImpl implements MatchResultDao {
    public static final String TABLE_NAME = "match_result";
//    public static final String ALL_COLUMNS = "participant_id, match_id, games_won, result";
    public static final String SELECT_ALL_WHERE =
        "SELECT (participant_id, match_id, games_won) " +
        "FROM " + TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME +
            " (participant_id, match_id, games_won) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public Map<MatchResult, Long> retrieveByMatchId(long matchId) {
        String sqlQuery = SELECT_ALL_WHERE + "(match_id = :matchId)";
        SqlParameterSource source = new MapSqlParameterSource("matchId", matchId);
        log.debug("In retrieveByMatchId, source = {}", source);
        
        // In this case, we need to return the participant id along with the matchResult so that the service can
        // set the matchResult.forParticipant properly based on the returned id.
        Map<MatchResult, Long> resultParticipantMap = new HashMap<>();
        try {
            Map<String, Object> columnMap = namedParameterJdbcTemplate.getJdbcOperations().queryForMap(sqlQuery);
            log.debug("in retrieveByMatchId, keyset: {}, values: {}", columnMap.keySet(), columnMap.values());
            for (String column : columnMap.keySet()) {
                MatchResult matchResult = new MatchResult();
                if (column.equals("participant_id")) {
                    log.debug("in retrieveByMatchId with column participant_id = {}", columnMap.get(column));
                    resultParticipantMap.put(matchResult, (Long) columnMap.get(column));
                } else if (column.equals("games_won")) {
                    log.debug("in retrieveByMatchId with column games_won = {}", columnMap.get(column));
                    matchResult.setGamesWon((Integer) columnMap.get(column));
                }
            }
            return resultParticipantMap;
        } catch (EmptyResultDataAccessException e) {
            log.debug("Retrieve yielded 0 results for matchId {}, returning null.", matchId);
            return null;
        }
    }
    
    @Override
    public MatchResult retrieveByParticipantIdAndMatchId(long participantId, long matchId) {
        String sqlQuery = SELECT_ALL_WHERE + "(participant_id = :participantId) AND (match_id = :matchId)";
        SqlParameterSource source = new MapSqlParameterSource().addValue("participantId", participantId)
                                                               .addValue("matchId", matchId);
        log.debug("In retrieveByParticipantIdAndMatchId, source = {}", source);
        try {
            MatchResult matchResult = namedParameterJdbcTemplate.queryForObject(sqlQuery,
                                                                                source,
                                                                                new MatchResultRowMapper());
            return matchResult;
        } catch (EmptyResultDataAccessException e) {
            log.debug("Retrieve yielded 0 results for participantId {} and matchId {}, returning null.",
                      participantId,
                      matchId);
            return null;
        }
    }
    
    // TODO
    @Override
    public Collection<MatchResult> retrieveAll() {
        return null;
    }
    
    @Override
    public long create(MatchResult matchResult) {
        String sqlQuery = INSERT + "(:participantId, :matchId, :gamesWon)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("participantId", matchResult.getForParticipant().getId())
                .addValue("matchId", matchResult.getOfMatch().getId())
                .addValue("gamesWon", matchResult.getGamesWon());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(sqlQuery,
                                              source,
                                              keyHolder) == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean update(MatchResult matchResult) {
        return false;
    }
    
    @Override
    public boolean delete(MatchResult matchResult) {
        return false;
    }
    
    @Override
    public void cleanup() {
    
    }
}
