package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.MatchResult;
import com.romco.persistence.dao.MatchResultDao;
import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Slf4j
public class MatchResultDaoImpl implements MatchResultDao {
    public static final String TABLE_NAME = "match_result";
//    public static final String ALL_COLUMNS = "participant_id, match_id, games_won, result";
    public static final String SELECT_ALL_WHERE =
        "SELECT (participant_id, match_id, games_won, result) " +
        "FROM " + TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME +
            " (participant_id, match_id, games_won, result) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public List<MatchResult> retrieveByMatchId(long matchId) {
        String sqlQuery = SELECT_ALL_WHERE + "(match_id = :matchId)";
        SqlParameterSource source = new MapSqlParameterSource("matchId", matchId);
        log.debug("In retrieveByMatchId, source = {}", source);
        try {
            List<MatchResult> matchResults = namedParameterJdbcTemplate.query(sqlQuery,
                                                                                source,
                                                                                new MatchResultRowMapper());
            return matchResults;
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
