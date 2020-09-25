package com.romco.bracketeer.persistence.dao

import com.romco.bracketeer.domain.Privilege
import com.romco.bracketeer.domain.Role
import com.romco.bracketeer.domain.User

interface UserDao: GenericDao<User> {
    fun retrieveByUsername(username: String): User?
}

interface RoleDao: GenericDao<Role> {
    fun retrieveByUser(userId: Long): Collection<Role>
}

interface PrivilegeDao : GenericDao<Privilege> {
    fun retrieveByRole(roleiD: Long): Collection<Privilege>
}