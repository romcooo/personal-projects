package com.romco.bracketeer.domain

import java.util.*

data class User(val username: String,
                val passwordHash: String,
                val lastUpdateDate: Date)

class Role(val name: String)