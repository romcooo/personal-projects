package com.romco.bracketeer.persistence.daoimpl;

import com.romco.bracketeer.domain.tournament.RuleSet;
import com.romco.bracketeer.persistence.dao.RuleSetDao;
import com.romco.bracketeer.persistence.rowmapper.RuleSetRowMapper;
import com.romco.bracketeer.persistence.util.NamedParameterJdbcTemplateHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Slf4j
@Repository
public class RuleSetDaoImpl implements RuleSetDao {
    public static final String TABLE_NAME = "rule_set";
    public static final String COL_POINTS_FOR_WIN = "points_for_win";
    public static final String COL_POINTS_FOR_LOSS = "points_for_loss";
    public static final String COL_POINTS_FOR_TIE = "points_for_tie";
    public static final String COL_DEFAULT_BEST_OFF = "default_best_of";
    public static final String COL_TOURNAMENT_ID = "tournament_id";

    // TODO figure a better way, this is awful
    public static final String SELECT_ALL_WHERE = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE ",
                                                          COL_POINTS_FOR_WIN, COL_POINTS_FOR_LOSS, COL_POINTS_FOR_TIE,
                                                          COL_DEFAULT_BEST_OFF, COL_TOURNAMENT_ID, TABLE_NAME);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplateHolder.get(dataSource);
    }

    @Override
    public RuleSet retrieveByTournamentId(long tournamentId) {
        String sqlQuery = SELECT_ALL_WHERE + " id = :tournamentId";
        SqlParameterSource source = new MapSqlParameterSource("tournamentId", tournamentId);
        RuleSet ruleSet = namedParameterJdbcTemplate.queryForObject(sqlQuery, source, new RuleSetRowMapper());
        log.debug("retrieveByTournamentId, tournamentId: {}, found ruleSet {}", tournamentId, ruleSet);
        return ruleSet;
    }

    @Override
    public Collection<RuleSet> retrieveAll() {
        return null;
    }

    @Override
    public long create(RuleSet ruleSet) {
        return 0;
    }

    @Override
    public boolean update(RuleSet ruleSet) {
        return false;
    }

    @Override
    public boolean delete(RuleSet ruleSet) {
        return false;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public RuleSet retrieve(long id) {
        return null;
    }
}
