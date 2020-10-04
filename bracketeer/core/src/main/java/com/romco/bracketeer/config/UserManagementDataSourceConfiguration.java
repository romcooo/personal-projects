//package com.romco.bracketeer.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ConfigurationProperties("app.datasource.bracketeer_um")
//public class UserManagementDataSourceConfiguration {
//
//    @Bean
//    public DataSourceProperties coreDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource coreDataSource() {
//        return coreDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//}
