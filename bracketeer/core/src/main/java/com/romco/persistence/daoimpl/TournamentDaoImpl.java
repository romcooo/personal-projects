package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Tournament;
import com.romco.persistence.dao.TournamentDao;
import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
//@Component - i think this really should be here if repository wasn't here but in xml, but it wasn't needed
@Repository
public class TournamentDaoImpl implements TournamentDao {
    public static final String TABLE_NAME = "tournament";
    public static final String SELECT_ALL = "SELECT id, code, name, type FROM " + TABLE_NAME;
    public static final String SELECT_ALL_WHERE = SELECT_ALL + " WHERE ";
    public static final String INSERT_ALL = "INSERT INTO " + TABLE_NAME + " (code, name, type) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
//    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public long create(Tournament tournament) {
//        SqlParameterSource source = new BeanPropertySqlParameterSource(tournament);
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("code", tournament.getCode())
                .addValue("name", tournament.getName())
                .addValue("type", tournament.getType().toString());
        String sqlQuery = INSERT_ALL + "(:code, :name, :type)";
        log.debug("In create, source = {}", source);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(sqlQuery,
                                              source,
                                              keyHolder) == 1) {
            tournament.setId(keyHolder.getKey().longValue());
            return keyHolder.getKey().longValue();
        } else {
            return -1;
        }
//        String sqlQuery = "INSERT INTO tournament (id, code, name, type) values (?, ?, ?, ?)";
//        Object[] args = new Object[] { tournament.getId(),
//                                       tournament.getCode(),
//                                       tournament.getName(),
//                                       tournament.getType()};
    }
    
    @Override
    public Tournament retrieve(long id) {
        SqlParameterSource source = new MapSqlParameterSource("id", id);
        String sqlQuery = SELECT_ALL_WHERE + "id = :id";
        log.debug("In retrieve, source = {}", source);
        try {
            Tournament tournament = namedParameterJdbcTemplate.queryForObject(sqlQuery,
                                                                              source,
                                                                              new TournamentRowMapper());
            return tournament;
        } catch (EmptyResultDataAccessException e) {
            log.debug("retrieve yielded 0 results for id {}, returning null object.", id);
            return null;
        }
//        Object[] args = new Object[] {id};
//        Tournament tournament = jdbcTemplate.queryForObject(sqlQuery, args, new TournamentRowMapper());
    }
    
    @Override
    public Tournament retrieve(String code) {
        SqlParameterSource source = new MapSqlParameterSource("code", code);
        String sqlQuery = SELECT_ALL_WHERE + "code = :code";
        try {
            Tournament tournament = namedParameterJdbcTemplate.queryForObject(sqlQuery,
                                                                              source,
                                                                              new TournamentRowMapper());
            log.debug("In retrieve, source = {}, found tournament: {}", source, tournament);
            return tournament;
        } catch (EmptyResultDataAccessException e) {
            log.debug("retrieve yielded 0 results for code {}, returning null object.", code);
            return null;
        }
//        Object[] args = new Object[] {code};
//        Tournament tournament = jdbcTemplate.queryForObject(sqlQuery, args, new TournamentRowMapper());
    }
    
    @Override
    public List<Tournament> retrieveAll() {
        String sqlQuery = SELECT_ALL;
        List<Tournament> tournamentList = namedParameterJdbcTemplate.query(sqlQuery, new TournamentRowMapper());
        log.info("Fetched tournamentList: {}", tournamentList);
        return tournamentList;
    }
    
    @Override
    public boolean delete(Tournament tournament) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(tournament);
        String sqlQuery = "DELETE FROM tournament where id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, source) == 1;
//        Object[] args = new Object[]{tournament.getId()};
//        String sqlQuery = "DELETE FROM tournament where id = ?";
    }
    
    @Override
    public boolean update(Tournament tournament) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("code", tournament.getCode())
                .addValue("name", tournament.getName())
                .addValue("type", tournament.getType().toString())
                .addValue("id", tournament.getId());
        String sqlQuery = UPDATE + "code = :code, name = :name, type = :type WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, source) == 1;
//        Object[] args = new Object[]{tournament.getId()};
//        jdbcTemplate.update()
    }
    
    @Override
    public void cleanup() {
//        String sqlQuery = "TRUNCATE TABLE " + TABLE_NAME;
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id > -1";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
