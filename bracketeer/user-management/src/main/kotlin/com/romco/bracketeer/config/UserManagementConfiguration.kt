package com.romco.bracketeer.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
@ConfigurationProperties("app.datasource.bracketeer-um")
open class UserManagementDataSourceConfiguration {

    @Bean
    open fun userManagementDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["userManagementDataSource"])
    open fun userManagementDataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/bracketeer_um?useSSL=false&serverTimezone=UTC")
                .username("brkt_um")
                .password("umPASS420")
        return dataSourceBuilder.build()
    }

    @Bean(name = ["userManagementTransactionManager"])
    @Autowired
    open fun userManagementTransactionManager(@Qualifier("userManagementDataSource") dataSource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}