package com.romco.bracketeer.persistence.util

import com.romco.bracketeer.config.UserManagementDataSourceConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

class User2RoleDaoUtil {
    companion object {
        private val TABLE_NAME = "${Constants.UM_SCHEMA}.user2role"
        val SELECT_ROLE_BY_USER = "SELECT role_id FROM $TABLE_NAME WHERE user_id = :userId"
        val SELECT_USER_BY_ROLE = "SELECT user_id FROM $TABLE_NAME WHERE role_id = :roleId"
    }
}

class Role2PrivilegeDaoUtil {
    companion object {
        private val TABLE_NAME = "${Constants.UM_SCHEMA}.role2privilege"
        val SELECT_PRIVILEGE_BY_ROLE = "SELECT privilege_id FROM $TABLE_NAME WHERE role_id = :roleId"
    }
}

abstract class WithDataSource {

    protected lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    @Autowired
    fun setDataSource(@Qualifier(UserManagementDataSourceConfiguration.USER_MANAGEMENT_DATA_SOURCE_BEAN_NAME) dataSource: DataSource) {
        namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
    }

}
