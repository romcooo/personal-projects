package com.romco.bracketeer

import com.romco.bracketeer.util.logger

// function can be used on literally anything in a return statement so that the value
// to be logged does not need to be stored in an intermediate variable
// example: "return Math.round(2.2).logged()" would return 2 (of type Long) and log "2" from class Long
inline fun <reified T> T.loggedTest(): T {
    val log = logger<T>()
    log.info(this.toString())
    return this
}

fun test(string: String): String {
    return string.get(0).toString().loggedTest()
}

fun test(some: Some): Some {
    return some.loggedTest()
}

fun main() {
    println(test("asd"))
    test(Some("roman", 3))
    println(Math.round(2.2).loggedTest())
}

class Some(val name: String, val id: Int) {
    override fun toString(): String {
        return "Some(name='$name', id=$id)"
    }
}