package com.romco.persistence.daoimpl;

import com.romco.domain.tournament.Round;
import com.romco.persistence.dao.RoundDao;

import javax.sql.DataSource;
import java.util.Collection;

public class RoundDaoImpl implements RoundDao {
//    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public Round retrieve(long id) {
        return null;
    }

    @Override
    public Collection<Round> retrieveAll() {
        return null;
    }

    @Override
    public long create(Round round) {
        return 0;
    }

    @Override
    public boolean update(Round round) {
        return false;
    }

    @Override
    public boolean delete(Round round) {
        return false;
    }

    @Override
    public void cleanup() {

    }
}
