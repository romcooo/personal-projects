package com.romco.bracketeer.persistence.daoimpl;

import com.romco.bracketeer.domain.tournament.MatchResult;
import com.romco.bracketeer.persistence.dao.MatchResultDao;
import com.romco.bracketeer.persistence.rowmapper.MatchResultMapResultSetExtractor;
import com.romco.bracketeer.persistence.rowmapper.MatchResultRowMapper;
import com.romco.bracketeer.persistence.util.NamedParameterJdbcTemplateHolder;
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
import java.util.Map;

@Slf4j
@Repository
public class MatchResultDaoImpl implements MatchResultDao {
    public static final String TABLE_NAME = "match_result";
    //    public static final String ALL_COLUMNS = "participant_id, match_id, games_won, result";
    public static final String SELECT_ALL_WHERE =
            "SELECT participant_id, match_id, games_won " +
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
        log.debug("In create, matchResult is {}, source is {}", matchResult, source);
        if (namedParameterJdbcTemplate.update(sqlQuery, source) == 1) {
            return 0;
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean update(MatchResult matchResult) {
        String sqlQuery = UPDATE + "games_won = :gamesWon " +
                "WHERE match_id = :matchId AND participant_id = :participantId";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("gamesWon", matchResult.getGamesWon())
                .addValue("matchId", matchResult.getOfMatch().getId())
                .addValue("participantId", matchResult.getForParticipant().getId());
        return namedParameterJdbcTemplate.update(sqlQuery, source) == 1;
    }
    
    @Override
    public boolean delete(MatchResult matchResult) {
        return false;
    }
    
    @Override
    public void cleanup() {
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE participant_id > -1";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
    
    @Override
    public Map<MatchResult, Long> retrieveByMatchId(long matchId) {
        String sqlQuery = SELECT_ALL_WHERE + "(match_id = :matchId)";
        SqlParameterSource source = new MapSqlParameterSource("matchId", matchId);
        log.debug("In retrieveByMatchId, source = {}", source);
        
        // In this case, we need to return the participant id along with the matchResult so that the service can
        // set the matchResult.forParticipant properly based on the returned id.
        Map<MatchResult, Long> resultParticipantMap;
        try {
            resultParticipantMap = namedParameterJdbcTemplate.query(sqlQuery, source, new MatchResultMapResultSetExtractor());
            log.debug("in retrieveByMatchId, resultMap = {}", resultParticipantMap);
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
}
