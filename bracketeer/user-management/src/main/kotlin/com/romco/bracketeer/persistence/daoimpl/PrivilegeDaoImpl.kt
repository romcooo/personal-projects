package com.romco.bracketeer.persistence.daoimpl

import com.romco.bracketeer.domain.Privilege
import com.romco.bracketeer.persistence.dao.PrivilegeDao
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Slf4j
@Repository
open class PrivilegeDaoImpl: PrivilegeDao {
    override fun retrieveByRole(roleiD: Long): Collection<Privilege> {
        TODO("Not yet implemented")
    }

    override fun setDataSource(dataSource: DataSource) {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(): Collection<Privilege> {
        TODO("Not yet implemented")
    }

    override fun create(t: Privilege): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Privilege): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(t: Privilege): Boolean {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }
}