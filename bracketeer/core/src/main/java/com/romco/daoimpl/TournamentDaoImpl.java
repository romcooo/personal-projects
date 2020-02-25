package com.romco.daoimpl;

import com.romco.dao.TournamentDao;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
//@Component - i think this really should be here if repository wasn't here but in xml, but it wasn't needed
@Repository("tournamentDao")
public class TournamentDaoImpl implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        try {
            log.debug("In setDataSource: {}", dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public boolean insert(Tournament tournament) {
        String sqlQuery = "INSERT INTO tournament (id, code, name, type) values (?, ?, ?, ?)";
        Object[] args = new Object[] { tournament.getId(),
                                       tournament.getCode(),
                                       tournament.getName(),
                                       tournament.getType()};
        return jdbcTemplate.update(sqlQuery, args) == 1;
    }
    
    @Override
    public Tournament select(long id) {
        String sqlQuery = "SELECT * FROM tournament where id = ?";
        Object[] args = new Object[] {id};
        Tournament tournament = jdbcTemplate.queryForObject(sqlQuery, args, new TournamentRowMapper());
        return tournament;
    }
    
    @Override
    public Tournament select(String code) {
        String sqlQuery = "SELECT * FROM tournament WHERE code = ?";
        Object[] args = new Object[] {code};
        Tournament tournament = jdbcTemplate.queryForObject(sqlQuery, args, new TournamentRowMapper());
        return tournament;
    }
    
    @Override
    public List<Tournament> selectAll() {
        String query = "SELECT * FROM tournament";
        try {
            log.debug("JdbcTemplate: {}", jdbcTemplate.getDataSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Tournament> tournamentList = jdbcTemplate.query(query, new TournamentRowMapper());
        log.info("Fetched tournamentList: {}", tournamentList);
        return tournamentList;
    }
    
    @Override
    public boolean delete(Tournament tournament) {
        String sqlQuery = "DELETE FROM tournament where id = " + tournament.getId();
        // delete here
        return false;
    }
    
    @Override
    public boolean update(Tournament tournament) {
        String sqlQuery = "UPDATE tournament SET ? = ?";
//        jdbcTemplate.update()
        return false;
    }
    
    @Override
    public void cleanup() {
        String sqlQuery = "TRUNCATE TABLE tournament";
        jdbcTemplate.execute(sqlQuery);
    }
}
