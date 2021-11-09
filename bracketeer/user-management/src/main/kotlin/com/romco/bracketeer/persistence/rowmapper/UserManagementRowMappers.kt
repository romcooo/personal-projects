package com.romco.bracketeer.persistence.rowmapper

import com.romco.bracketeer.domain.Privilege
import com.romco.bracketeer.domain.Role
import com.romco.bracketeer.domain.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(
            id = rs.getLong("id"),
            username = rs.getString("username"),
            passwordHash = rs.getString("password_hash"),
            creationDate = rs.getDate("creation_date"),
            lastPasswordChangeDate = rs.getDate("last_password_change_date"),
            email = rs.getString("email"),
            lastUpdateDate = rs.getDate("last_update_date")
        )
    }
}

class RoleRowMapper : RowMapper<Role> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Role? {
        return Role(
            rs.getLong("id"),
            rs.getString("name")
        )
    }
}

class PrivilegeRowMapper : RowMapper<Privilege> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Privilege? {
        return Privilege(
            rs.getLong("id"),
            rs.getString("name")
        )
    }
}