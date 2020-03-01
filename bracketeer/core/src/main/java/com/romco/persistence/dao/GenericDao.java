package com.romco.persistence.dao;

import com.romco.persistence.util.NamedParameterJdbcTemplateHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.Collection;

public interface GenericDao<T> {
    
    void setDataSource(DataSource dataSource);
    
    // retrieves by id, can be null if 0 records found
    T retrieve(long id);
    
    // retrieves all
    Collection<T> retrieveAll();
    
    // creates and returns id of the newly created record. In case of failure, returns -1.
    long create(T t);
    
    // updates by id, returns true if success.
    boolean update(T t);
    
    // deletes by id, returns true if success.
    boolean delete(T t);
    
    void cleanup();
}
