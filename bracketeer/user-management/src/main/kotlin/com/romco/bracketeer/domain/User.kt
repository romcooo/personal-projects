package com.romco.bracketeer.domain

data class User(val username: String, val passwordHash: String)
class Role(val name: String)