package com.romco.bracketeer.persistence.dao;

public interface GenericSimplePKDao<T> extends GenericDao<T> {
    // retrieves by id, can be null if 0 records found
    T retrieve(long id);
}
