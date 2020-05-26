package com.romco.bracketeer.persistence.util

import com.romco.bracketeer.persistence.dao.UserDao
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Slf4j
@Component
open class DbInit {

    val log = logger<DbInit>()

    @Autowired
    lateinit var userDao: UserDao

    @PostConstruct
    fun initialize() {
        userDao.cleanup()
        log.info("in init userDao")
    }
}

