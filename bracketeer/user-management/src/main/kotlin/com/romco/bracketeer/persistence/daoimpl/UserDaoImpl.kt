package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.User
import com.romco.bracketeer.persistence.dao.UserDao
import com.romco.bracketeer.persistence.rowmapper.UserRowMapper
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.sql.DataSource

@Slf4j
@Repository
open class UserDaoImpl: UserDao {

    val log = logger<UserDaoImpl>()

    private val usernameCol = "username"
    private val passwordHashCol = "password_hash"
    private val createdDateCol = "created_date"

    private val SCHEMA = "bracketeer_um"
    private val TABLE_NAME = "$SCHEMA.user"
    private val SELECT_ALL_WHERE = "SELECT id, username, password_hash FROM $TABLE_NAME WHERE"
    private val INSERT = "INSERT INTO $TABLE_NAME (username, password_hash, last_update_date) VALUES"
    private val UPDATE = "UPDATE $TABLE_NAME SET"

    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    @Autowired
    override fun setDataSource(dataSource: DataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    }

    override fun retrieveAll(): Collection<User> {
        TODO("Not yet implemented")
    }

    override fun retrieveByUsername(username: String): User? {
        val sqlQuery = "$SELECT_ALL_WHERE username = :username"
        val source = MapSqlParameterSource("username", username)
        val keyHolder = GeneratedKeyHolder()
        val user = namedParameterJdbcTemplate.queryForObject(sqlQuery, source, UserRowMapper())
        log.info("retrieved user $user")
        return user
    }

    override fun create(user: User): Long {
        val sqlQuery = "$INSERT (:username, :passwordHash, :lastUpdateDate)"
        val source = MapSqlParameterSource()
                .addValue("username", user.username)
                .addValue("passwordHash", user.passwordHash)
                .addValue("lastUpdateDate", LocalDateTime.now())
        val keyHolder = GeneratedKeyHolder()
        log.info("creating user in database: $user")
        return if (namedParameterJdbcTemplate.update(sqlQuery, source, keyHolder) == 1) {
            keyHolder.key?.toLong() ?: -1
        } else {
            -1
        }
    }

    override fun update(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        val sqlQuery = "DELETE FROM $TABLE_NAME WHERE id > -1"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        log.info("in cleanup of UserDaoImpl")
    }
}