package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.User
import com.romco.bracketeer.persistence.dao.UserDao
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Slf4j
@Repository
class UserDaoImpl: UserDao {

    private val SCHEMA = "bracketeer_um"
    private val TABLE_NAME = "$SCHEMA.user"
    private val SELECT_ALL_WHERE = "SELECT id, username, password FROM $TABLE_NAME WHERE"
    private val INSERT = "INSERT INTO $TABLE_NAME (username, password) VALUES"
    private val UPDATE = "UPDATE $TABLE_NAME SET"

    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate


    @Autowired
    override fun setDataSource(dataSource: DataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    }

    override fun retrieveAll(): Collection<User> {
        TODO("Not yet implemented")
    }

    override fun create(user: User): Long {
        val sqlQuery = "$INSERT (:username, :password)"
        val source = MapSqlParameterSource()
                .addValue("username", user.username)
                .addValue("password", user.password)
        val keyHolder = GeneratedKeyHolder()
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
    }
}