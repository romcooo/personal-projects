package com.romco.bracketeer.persistence.dao

import javax.sql.DataSource

interface UserDao {
    fun setDataSource(dataSource: DataSource?)

    // retrieves all
    fun <T> retrieveAll(): Collection<T?>?

    // creates and returns id of the newly created record. In case of failure, returns -1.
    fun <T> create(t: T?): Long

    // updates by id, returns true if success.
    fun <T> update(t: T?): Boolean

    // deletes by id, returns true if success.
    fun <T> delete(t: T?): Boolean

    fun cleanup()
}