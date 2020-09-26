package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.persistence.util.Constants

class User2RoleDaoUtil {
    companion object {
        private val TABLE_NAME = "${Constants.UM_SCHEMA}.user2role"
        val SELECT_ROLE_BY_USER = "SELECT role_id FROM $TABLE_NAME WHERE user_id = :userId"
        val SELECT_USER_BY_ROLE = "SELECT user_id FROM $TABLE_NAME WHERE role_id = :roleId"
    }
}