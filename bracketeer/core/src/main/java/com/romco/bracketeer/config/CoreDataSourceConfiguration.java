package com.romco.bracketeer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
//@ConfigurationProperties("app.datasource.bracketeer")
//@PropertySource("classpath:application.properties")
public class CoreDataSourceConfiguration {

    @Bean
    @Primary
    public DataSourceProperties coreDataSourceProperties() {
        return new DataSourceProperties();
    }

    //TODO this should be retrieved from application.properties

    @Bean(name = "coreDataSource")
    @Primary
    public DataSource coreDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/bracketeer?useSSL=false&serverTimezone=UTC");
        dataSourceBuilder.username("brkt_core");
        dataSourceBuilder.password("corePASS420");
        return dataSourceBuilder.build();
//        return coreDataSourceProperties().initializeDataSourceBuilder()
//                                         .driverClassName("com.mysql.cj.jdbc.Driver")
//                                         .url("jdbc:mysql://127.0.0.1:3306/bracketeer?user=brkt_core&useSSL=false&serverTimezone=UTC")
//                                         .build();
    }

    @Bean(name = "coreTransactionManager")
    @Autowired
    DataSourceTransactionManager coreTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
