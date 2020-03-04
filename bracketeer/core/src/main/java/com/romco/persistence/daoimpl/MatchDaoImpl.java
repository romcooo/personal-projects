package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Match;
import com.romco.persistence.dao.MatchDao;
import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

public class MatchDaoImpl implements MatchDao {
    
    public static final String TABLE_NAME = "match";
    //TODO
    public static final String SELECT_ALL_WHERE = "SELECT round_id, result, is_bye FROM " + TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (round_id, result, is_bye) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public List<Match> retrieveByRoundId(long roundId) {
        String sqlQuery = SELECT_ALL_WHERE + "(round_id = :roundId)";
        SqlParameterSource source = new MapSqlParameterSource().addValue("roundId", roundId);
        namedParameterJdbcTemplate.query(sqlQuery, source, new MatchRowMapper());
        return null;
    }
    
    // TODO
    @Override
    public Match retrieve(long id) {
        return null;
    }
    
    // TODO
    @Override
    public Collection<Match> retrieveAll() {
        return null;
    }
    
    // TODO
    @Override
    public long create(Match match) {
        
        return 0;
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
    
    }
}
