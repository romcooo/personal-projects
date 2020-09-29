package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Privilege
import com.romco.bracketeer.persistence.dao.PrivilegeDao
import com.romco.bracketeer.persistence.rowmapper.PrivilegeRowMapper
import com.romco.bracketeer.persistence.util.Constants
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Slf4j
@Repository
open class PrivilegeDaoImpl: PrivilegeDao {

    val log = logger<PrivilegeDaoImpl>()

    private val TABLE_NAME = "${Constants.UM_SCHEMA}.privilege"
    private val SELECT_ALL_WHERE = "SELECT id, name FROM $TABLE_NAME WHERE"


    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    override fun retrieveByRole(roleId: Long): Collection<Privilege> {
        val sqlQuery = "$SELECT_ALL_WHERE id IN (${Role2PrivilegeDaoUtil.SELECT_PRIVILEGE_BY_ROLE})"
        val source = MapSqlParameterSource("roleId", roleId)
        val privileges: List<Privilege> = namedParameterJdbcTemplate.query(sqlQuery, source, PrivilegeRowMapper())
        log.info("Retrieved privileges: $privileges")
        return privileges
    }

    override fun setDataSource(dataSource: DataSource) {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(): Collection<Privilege> {
        TODO("Not yet implemented")
    }

    override fun create(t: Privilege): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Privilege): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(t: Privilege): Boolean {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }
}