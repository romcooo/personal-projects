package com.romco.bracketeer.domain

import java.util.*

// TODO changed structure, this class needs to be aligned

data class User(var id: Long = 0,
                var username: String,
                var passwordHash: String,
                var lastUpdateDate: Date?,
                var roles: Collection<Role>? = emptyList()) {

    companion object {
        fun buildUser(username: String, passwordHash: String, lastUpdateDate: Date): User {
            return User(username = username, passwordHash = passwordHash, lastUpdateDate =  lastUpdateDate)
        }
    }
}

data class Role(var id: Long = 0,
                var name: String,
                var privileges: Collection<Privilege> = emptyList(),
                var ofUsers: Collection<User> = emptyList()) {

    companion object {
        fun buildRole(name: String): Role {
            return Role(name = name)
        }
    }

}

data class Privilege(var id: Long = 0,
                     var name: String,
                     var ofRoles: Collection<Role> = emptyList()) {

    companion object {
        fun buildPrivilege(name: String) : Privilege {
            return Privilege(name = name)
        }
    }
}
