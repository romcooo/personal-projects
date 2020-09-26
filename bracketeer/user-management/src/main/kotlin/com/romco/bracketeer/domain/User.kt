package com.romco.bracketeer.domain

import java.util.*

// TODO changed structure, this class needs to be aligned

data class User(var id: Long = -1,
                var username: String,
                var passwordHash: String,
                var lastUpdateDate: Date?,
                var roles: Collection<Role>? = emptyList()) {

    // required for proper java interoperability
    constructor(username: String, passwordHash: String, lastUpdateDate: Date):
            this() {
        this.username = username
        this.passwordHash = passwordHash
        this.lastUpdateDate = lastUpdateDate
    }

    // TODO solve it better, this is really ugly (needed because of compiler complaints and java interop)
    private constructor() {
        User(-1, "", "", Date(), emptyList())
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
