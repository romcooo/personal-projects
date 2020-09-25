package com.romco.bracketeer.domain

import java.util.*

data class User(var id: Long? = -1,
                var username: String,
                var passwordHash: String,
                var lastUpdateDate: Date,
                var roles: Collection<Role>? = emptyList()) {

    // required for proper java interoperability
    constructor(username: String, passwordHash: String, lastUpdateDate: Date) {
        User(username, passwordHash, lastUpdateDate)
    }
}

// TODO: add constructor for java interop for both Role and Privilege

class Role(var id: Long = -1,
           var name: String,
           var privileges: Collection<Privilege> = emptyList(),
           var ofUsers: Collection<User> = emptyList()) {

    constructor(name: String) {
        Role(name)
    }

}

class Privilege(var id: Long = 1,
                var name: String,
                var ofRoles: Collection<Role> = emptyList()) {

    constructor(name: String){
        Privilege(name)
    }
}
