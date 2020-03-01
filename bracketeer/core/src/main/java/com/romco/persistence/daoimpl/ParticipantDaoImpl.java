package com.romco.persistence.daoimpl;

import com.romco.domain.participant.Participant;
import com.romco.persistence.dao.ParticipantDao;
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
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class ParticipantDaoImpl implements ParticipantDao {
    public static final String TABLE_NAME = "participant";
    public static final String SELECT_ALL_WHERE = "SELECT id, code, name, tournament_id FROM " + TABLE_NAME + " WHERE ";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (code, name, tournament_id) VALUES ";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET ";
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }
    
    @Override
    public Participant retrieve(long id) {
        SqlParameterSource source = new MapSqlParameterSource("id", id);
        String sqlQuery = SELECT_ALL_WHERE + "id = :id";
        log.debug("In retrieve, source = {}", source);
        try {
            Participant participant = namedParameterJdbcTemplate.queryForObject(sqlQuery,
                                                                              source,
                                                                              new ParticipantRowMapper());
            return participant;
        } catch (EmptyResultDataAccessException e) {
            log.debug("retrieve yielded 0 results for id {}, returning null object.", id);
            return null;
        }
    }
    
    @Override
    public List<Participant> retrieveByTournamentId(long tournamentId) {
        String sqlQuery = SELECT_ALL_WHERE + "(tournament_id = :tournamentId)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("tournamentId", tournamentId);
        try {
            List<Participant> participants = namedParameterJdbcTemplate.query(sqlQuery,
                                                                              source,
                                                                              new ParticipantRowMapper());
            log.debug("In retrieve, source = {}, found participants: {}", source, participants);
            return participants;
        } catch (EmptyResultDataAccessException e) {
            log.debug("retrieve yielded 0 results for tournamentId {}, returning empty list.", tournamentId);
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<Participant> retrieveAll() {
        return null;
    }
    
    @Override
    public long create(Participant participant) {
        String sqlQuery = INSERT + "(:code, :name, :tournamentId)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("code", participant.getCode())
                .addValue("name", participant.getName())
                .addValue("tournamentId", participant.getOfTournament().getId());
        log.debug("In create, source = {}", source);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(sqlQuery, source, generatedKeyHolder) == 1) {
            participant.setId(generatedKeyHolder.getKey().longValue());
            return generatedKeyHolder.getKey().longValue();
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean update(Participant participant) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(participant);
        String sqlQuery = UPDATE + "code = :code, name = :name WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, source) == 1;
    }
    
    @Override
    public boolean delete(Participant participant) {
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id = :id";
        SqlParameterSource source = new MapSqlParameterSource("id", participant.getId());
        return namedParameterJdbcTemplate.update(sqlQuery, source) == 1;
    }
    
    @Override
    public void cleanup() {
//        String sqlQuery = "TRUNCATE TABLE " + TABLE_NAME;
        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id > -1";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sqlQuery);
    }
}
