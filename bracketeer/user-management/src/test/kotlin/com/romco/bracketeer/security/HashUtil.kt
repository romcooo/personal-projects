package com.romco.bracketeer.security

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.transaction.annotation.Transactional


class HashUtil {

    @Test
    fun encodePassword() {
        assertTrue(PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .matches("password", encodePassword("password")))
    }
}
