package com.romco.bracketeer.security

import com.romco.bracketeer.domain.User
import com.romco.bracketeer.persistence.daoimpl.UserDaoImpl
import com.romco.bracketeer.util.logger
import com.sun.tools.javac.Main
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.random.Random


const val ID = "$31$"
const val DEFAULT_COST = 16
const val ALGORITHM = "PBKDF2WithHmacSHA512"

//TODO this method can be removed
fun hashPassword(password: String): Password {
    val salt = ByteArray(16)
    Random.nextBytes(salt)
    val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
    val f = SecretKeyFactory.getInstance(ALGORITHM)
    val hash = f.generateSecret(spec).encoded
    val enc: Base64.Encoder = Base64.getEncoder()
    System.out.printf("salt: %s%n", enc.encodeToString(salt))
    System.out.printf("hash: %s%n", enc.encodeToString(hash))
    return Password(hash.toString(), salt.toString())
}

fun encodePassword(password: String): String? {
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
