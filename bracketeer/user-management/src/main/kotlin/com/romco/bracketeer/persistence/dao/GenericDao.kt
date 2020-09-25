package com.romco.bracketeer.persistence.dao

import javax.sql.DataSource

interface GenericDao<T> {
    fun setDataSource(dataSource: DataSource)
    // retrieves all
    fun retrieveAll(): Collection<T>
    // creates and returns id of the newly created record. In case of failure, returns -1.
    fun create(t: T): Long
    // updates by id, returns true if success.
    fun update(t: T): Boolean
    // deletes by id, returns true if success.
    fun delete(t: T): Boolean
    // deletes all test records (id < 0)
    fun cleanup()
}