package com.romco.bracketeer.persistence.dao

import com.romco.bracketeer.domain.User
import javax.sql.DataSource

interface UserDao {
    fun setDataSource(dataSource: DataSource)

    // retrieves all
    fun retrieveAll(): Collection<User>

    fun retrieveByUsername(username: String): User?

    // creates and returns id of the newly created record. In case of failure, returns -1.
    fun create(user: User): Long

    // updates by id, returns true if success.
    fun update(user: User): Boolean

    // deletes by id, returns true if success.
    fun delete(user: User): Boolean

    fun cleanup()
}