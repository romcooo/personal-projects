package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Role
import com.romco.bracketeer.persistence.dao.RoleDao
import com.romco.bracketeer.persistence.rowmapper.RoleRowMapper
import com.romco.bracketeer.persistence.util.Constants
import com.romco.bracketeer.persistence.util.User2RoleDaoUtil
import com.romco.bracketeer.persistence.util.WithDataSource
import com.romco.bracketeer.util.logged
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.stereotype.Repository

@Slf4j
@Repository
open class RoleDaoImpl: RoleDao, WithDataSource() {

    val log = logger<RoleDaoImpl>()

    private val nameCol = "name"

    private val TABLE_NAME = "role"
    private val SELECT_ALL_WHERE = "SELECT id, name FROM $TABLE_NAME WHERE"
    private val INSERT = "INSERT INTO $TABLE_NAME (name) VALUES"
    private val UPDATE = "UPDATE $TABLE_NAME SET"

//    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate


    override fun retrieveByUser(userId: Long): Collection<Role> {
        val sqlQuery = "$SELECT_ALL_WHERE id IN (${User2RoleDaoUtil.SELECT_ROLE_BY_USER})"
        val source = MapSqlParameterSource("user_id", userId)
        val roles: List<Role> = namedParameterJdbcTemplate.query(sqlQuery, source, RoleRowMapper())
//        log.info("retrieved roles: $roles")
        return roles.logged("Returning roles:")
    }

//    @Autowired
//    override fun setDataSource(@Qualifier(USER_MANAGEMENT_DATA_SOURCE_BEAN_NAME) dataSource: DataSource) {
//        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
//    }

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
//        val sqlQuery = "DELETE FROM $TABLE_NAME WHERE id > -1"
//        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
//        log.info("Cleaned up $TABLE_NAME: $sqlQuery")

        log.info("$TABLE_NAME will not be cleaned up, contains parametrized data.")
    }
}