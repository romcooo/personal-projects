package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Role
import com.romco.bracketeer.persistence.dao.RoleDao
import com.romco.bracketeer.persistence.rowmapper.RoleRowMapper
import com.romco.bracketeer.persistence.util.Constants
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Slf4j
@Repository
open class RoleDaoImpl: RoleDao {

    val log = logger<RoleDaoImpl>()

    private val nameCol = "name"

    private val TABLE_NAME = "${Constants.UM_SCHEMA}.role"
    private val SELECT_ALL_WHERE = "SELECT id, name FROM $TABLE_NAME WHERE"
    private val INSERT = "INSERT INTO $TABLE_NAME (name) VALUES"
    private val UPDATE = "UPDATE $TABLE_NAME SET"

    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate


    override fun retrieveByUser(userId: Long): Collection<Role> {
        val sqlQuery = "$SELECT_ALL_WHERE id IN (${User2RoleDaoUtil.SELECT_ROLE_BY_USER})"
        val source = MapSqlParameterSource("user_id", userId)
        val roles: List<Role> = namedParameterJdbcTemplate.query(sqlQuery, source, RoleRowMapper())
        log.info("retrieved roles: $roles")
        return roles
    }

    @Autowired
    override fun setDataSource(dataSource: DataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    }

    override fun retrieveAll(): Collection<Role> {
        TODO("Not yet implemented")
    }

    override fun create(t: Role): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Role): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(t: Role): Boolean {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }
}