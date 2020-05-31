package com.romco.bracketeer.persistence.rowmapper

import com.romco.bracketeer.domain.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        val user = User(rs.getString("username"), rs.getString("password_hash"))
        return user
    }
}