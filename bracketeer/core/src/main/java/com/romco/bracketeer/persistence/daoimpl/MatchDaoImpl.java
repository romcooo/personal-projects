package com.romco.bracketeer.persistence.daoimpl;

import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.persistence.dao.MatchDao;
import com.romco.bracketeer.persistence.rowmapper.MatchRowMapper;
import com.romco.bracketeer.persistence.util.NamedParameterJdbcTemplateHolder;
import com.romco.bracketeer.persistence.util.NullKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class MatchDaoImpl implements MatchDao {
    
    public static final String TABLE_NAME = "`match`";
    //TODO
    public static final String SELECT_ALL_WHERE = "SELECT id, round_id, is_bye, match_number FROM " +
            TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (round_id, is_bye, match_number) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public List<Match> retrieveByRoundId(long roundId) {
        String sqlQuery = SELECT_ALL_WHERE + "round_id = :roundId";
        SqlParameterSource source = new MapSqlParameterSource().addValue("roundId", roundId);
        return namedParameterJdbcTemplate.query(sqlQuery, source, new MatchRowMapper());
    }
    
    // TODO
    @Override
    public Match retrieve(long id) {
        return null;
    }
    
    // TODO
    @Override
    public Collection<Match> retrieveAll() {
        return Collections.emptyList();
    }
    
    // TODO
    @Override
    public long create(Match match) {
        String sqlQuery = INSERT + "(:roundId, :isBye, :matchNumber)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("roundId", match.getOfRound().getId())
                .addValue("isBye", match.isBye())
                .addValue("matchNumber", match.getMatchNumber());
        var keyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(sqlQuery,
                                              source,
                                              keyHolder) == 1) {
            var k = keyHolder.getKey();
            if (k == null) throw new NullKeyException("For some reason, key is null when creating DB Entry.");
            return k.longValue();
        } else {
            log.debug("create failed for match {}", match);
            return -1;
        }
    }
    
    // TODO
    @Override
    public boolean update(Match match) {
        return false;
    }
    
    // TODO
    @Override
    public boolean delete(Match match) {
        return false;
    }
    
    // TODO
    @Override
    public void cleanup() {
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id > -1";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
