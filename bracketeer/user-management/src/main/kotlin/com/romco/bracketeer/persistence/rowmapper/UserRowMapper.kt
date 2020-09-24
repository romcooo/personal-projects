package com.romco.bracketeer.persistence.rowmapper

import com.romco.bracketeer.domain.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getDate("last_update_date"))
    }
}