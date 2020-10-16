package com.romco.bracketeer.persistence.util

import com.romco.bracketeer.persistence.dao.PrivilegeDao
import com.romco.bracketeer.persistence.dao.RoleDao
import com.romco.bracketeer.persistence.dao.UserDao
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Slf4j
@Component
open class DbInit: WithDataSource() {

    val log = logger<DbInit>()

    @Autowired
    lateinit var userDao: UserDao

    @Autowired
    lateinit var roleDao: RoleDao

    @Autowired
    lateinit var privilegeDao: PrivilegeDao

//    @Autowired
//    fun setDataSource(@Qualifier(USER_MANAGEMENT_DATA_SOURCE_BEAN_NAME) dataSource: DataSource) {
//        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
//    }

    //    @PostConstruct
    @EventListener
    fun initialize(event: ContextRefreshedEvent) {
        userDao.cleanup()
        roleDao.cleanup()
        privilegeDao.cleanup()

        // init here
        //TODO ok so this is really ugly, rework it later
//        var sqlQuery = "USE bracketeer_um"
//        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        var sqlQuery = "INSERT IGNORE  INTO `user` VALUES (-1,'bracketeer','{bcrypt}\$2a\$10\$ehfPb/GiMFugw26ObJzja.iflGYnvtNfLjTwBxgB3eTQMZL16pedW','2020-09-26 20:41:22',NULL,'test@test.com',NULL);"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        sqlQuery = "INSERT IGNORE  INTO `role` (id, name) VALUES (-1, 'basic_user');"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        sqlQuery = "INSERT IGNORE  INTO `user2role` (user_id, role_id) VALUES (-1, -1);"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        sqlQuery = "INSERT IGNORE  INTO `privilege` (id, name) VALUES (-1, 'user_access');"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        sqlQuery = "INSERT IGNORE  INTO `role2privilege` (role_id, privilege_id) VALUES (-1, -1);"
        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
//        sqlQuery = "USE bracketeer"
//        namedParameterJdbcTemplate.jdbcOperations.execute(sqlQuery)
        log.info("Created test data.")
    }

}
