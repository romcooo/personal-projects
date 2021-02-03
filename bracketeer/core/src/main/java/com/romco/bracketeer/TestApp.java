package com.romco.bracketeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// just a test of whether it can run from this module
@SpringBootApplication
@ComponentScan(basePackages = "com.romco")
public class TestApp {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

}