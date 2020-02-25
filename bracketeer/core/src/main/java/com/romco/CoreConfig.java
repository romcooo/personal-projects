package com.romco;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.romco")
@ImportResource("classpath:beans.xml")
@PropertySource("classpath:application.properties")
public class CoreConfig {

}
