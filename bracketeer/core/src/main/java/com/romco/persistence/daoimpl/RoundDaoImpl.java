package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Round;
import com.romco.persistence.dao.RoundDao;
import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
public class RoundDaoImpl implements RoundDao {
    public static final String TABLE_NAME = "round";
    public static final String SELECT_ALL_WHERE = "SELECT id, tournament_id, round_number, best_of FROM " + TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (tournament_id, round_number, best_of) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }

    @Override
    public Round retrieve(long id) {
        String sqlQuery = SELECT_ALL_WHERE + " id = :id";
        SqlParameterSource source = new MapSqlParameterSource("id", id);
        Round round = namedParameterJdbcTemplate.queryForObject(sqlQuery, source, new RoundRowMapper());
        return round;
    }
    
    @Override
    public List<Round> retrieveByTournamentId(long tournamentId) {
        String sqlQuery = SELECT_ALL_WHERE + " tournament_id = :tournamentId";
        SqlParameterSource source = new MapSqlParameterSource("tournamentId", tournamentId);
        log.debug("In selectByTournamentId, source: {}", source);
        List<Round> rounds = namedParameterJdbcTemplate.query(sqlQuery, source, new RoundRowMapper());
        return rounds;
    }
    
    // TODO
    @Override
    public Collection<Round> retrieveAll() {
        return null;
    }

    @Override
    public long create(Round round) {
        String sqlQuery = INSERT + "(:tournamentId, :roundNumber, :bestOf)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("tournamentId", round.getOfTournament().getId())
                .addValue("roundNumber", round.getRoundNumber())
                .addValue("bestOf", round.getBestOf());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("In create for round {}, source: {}", round, source);
        if (namedParameterJdbcTemplate.update(sqlQuery, source, keyHolder) == 1) {
            round.setId(keyHolder.getKey().longValue());
            return keyHolder.getKey().longValue();
        } else {
            return -1;
        }
    }
    
    // TODO
    @Override
    public boolean update(Round round) {
        return false;
    }
    
    // TODO
    @Override
    public boolean delete(Round round) {
        return false;
    }

    @Override
    public void cleanup() {
//        String sqlQuery = "TRUNCATE TABLE " + TABLE_NAME;
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id > -1";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
