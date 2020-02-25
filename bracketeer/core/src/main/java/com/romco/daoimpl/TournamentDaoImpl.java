package com.romco.daoimpl;

import com.romco.dao.TournamentDao;
import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Slf4j
//@Component - i think this really should be here
public class TournamentDaoImpl implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

//    @Autowired - it's done in xml
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
        return false;
    }
    
    @Override
    public Tournament select(long id) {
        String query = "SELECT * FROM tournament where id = " + id;
        Tournament tournament = jdbcTemplate.query(query, new TournamentRowMapper()).get(0);
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
        return false;
    }
    
    @Override
    public boolean update(Tournament tournament) {
        return false;
    }
    
    @Override
    public void cleanup() {
    
    }
}
