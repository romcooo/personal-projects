package com.romco.bracketeer.persistence.dao

import com.romco.bracketeer.config.UserManagementDataSourceConfiguration
import com.romco.bracketeer.config.UserManagementDataSourceConfiguration.Companion.USER_MANAGEMENT_DATA_SOURCE_BEAN_NAME
import com.romco.bracketeer.domain.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
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
