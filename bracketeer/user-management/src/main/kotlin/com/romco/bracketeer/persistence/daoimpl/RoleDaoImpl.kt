package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Role
import com.romco.bracketeer.persistence.dao.RoleDao
import com.romco.bracketeer.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Slf4j
@Repository
open class RoleDaoImpl: RoleDao {

    val log = logger<RoleDaoImpl>()

    override fun retrieveByUser(userId: Long): Collection<Role> {
        TODO("Not yet implemented")
    }

    override fun setDataSource(dataSource: DataSource) {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(): Collection<Role> {
        TODO("Not yet implemented")
    }

    override fun create(t: Role): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Role): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(t: Role): Boolean {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }
}