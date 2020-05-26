package com.romco.bracketeer.security

import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.random.Random


const val ID = "$31$"
const val DEFAULT_COST = 16
const val ALGORITHM = "PBKDF2WithHmacSHA512"

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

