package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Privilege
import com.romco.bracketeer.persistence.dao.PrivilegeDao
import com.romco.bracketeer.persistence.rowmapper.PrivilegeRowMapper
import com.romco.bracketeer.persistence.util.Constants
import com.romco.bracketeer.persistence.util.Role2PrivilegeDaoUtil
import com.romco.bracketeer.persistence.util.WithDataSource
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.stereotype.Repository

@Slf4j
@Repository
open class PrivilegeDaoImpl: PrivilegeDao, WithDataSource() {

    val log = logger<PrivilegeDaoImpl>()

    private val TABLE_NAME = "privilege"
    private val SELECT_ALL_WHERE = "SELECT id, name FROM $TABLE_NAME WHERE"


//    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    override fun retrieveByRole(roleId: Long): Collection<Privilege> {
        val sqlQuery = "$SELECT_ALL_WHERE id IN (${Role2PrivilegeDaoUtil.SELECT_PRIVILEGE_BY_ROLE})"
        val source = MapSqlParameterSource("roleId", roleId)
        val privileges: List<Privilege> = namedParameterJdbcTemplate.query(sqlQuery, source, PrivilegeRowMapper())
        log.info("Retrieved privileges: $privileges")
        return privileges
    }

//    @Autowired
//    override fun setDataSource(@Qualifier(USER_MANAGEMENT_DATA_SOURCE_BEAN_NAME) dataSource: DataSource) {
//        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
//    }

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
//        val sqlQuery = "DELETE FROM $TABLE_NAME WHERE id > -1"
//        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
//        log.info("Cleaned up $TABLE_NAME: $sqlQuery")

        log.info("$TABLE_NAME will not be cleaned up, contains parametrized data.")
    }
}