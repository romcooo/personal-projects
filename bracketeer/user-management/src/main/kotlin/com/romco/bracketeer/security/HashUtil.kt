package com.romco.bracketeer.security

import com.romco.bracketeer.domain.User
import com.romco.bracketeer.util.logger
import com.sun.tools.javac.Main
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder


fun encodePassword(password: String): String {
    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    val hash = passwordEncoder.encode(password)
    logger<Main>().info("encoding password, resulting hash is $hash")
    return hash
}

fun verifyUser(user: User, password: String): Boolean {
    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    return passwordEncoder.matches(password, user.passwordHash)
}


fun main() {
    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    val password = "bracketeer"
    val hashed = passwordEncoder.encode(password)
    println("password = $password, hashed = $hashed")
    val doesMatch = passwordEncoder.matches(password, hashed)
    println(doesMatch)
}
