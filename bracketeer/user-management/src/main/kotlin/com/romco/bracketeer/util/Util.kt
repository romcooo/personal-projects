package com.romco.bracketeer.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

// function can be used on literally anything in a return statement so that the value
// to be logged does not need to be stored in an intermediate variable
// example: "return Math.round(2.2).logged()" would return 2 (of type Long) and log "2" from class Long
inline fun <reified T> T.logged(): T {
    return logged("")
}

inline fun <reified T> T.logged(message: String): T {
    val log = logger<T>()
    log.info("$message ${this.toString()}")
    return this
}