package com.romco;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = "com.romco")
@ImportResource("classpath:beans.xml")
public class CoreConfig {

//    @Bean
//    public String tournamentDao() {
//        return "tournamentDao";
//    }
//
//    @Bean
//    public String dataSource() {
//        return "dataSource";
//    }
}
