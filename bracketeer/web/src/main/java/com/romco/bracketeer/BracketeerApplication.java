package com.romco.bracketeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.romco")
public class BracketeerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(BracketeerApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(BracketeerApplication.class, args);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println(name);
		}
	}

}
