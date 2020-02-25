package com.romco.bracketeer;

import com.romco.bracketeer.controller.TournamentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BracketeerApplicationTests {

	@Autowired
	private TournamentController tournamentController;
	
//	@Test
//	void contextLoads() {
//		assertThat(tournamentController).isNotNull();
//	}

}

/*

@SpringBootTest
@ContextConfiguration(locations={"classpath:beans.xml"})
//@ImportResource("classpath:beans.xml")
//@TestPropertySource("classpath:application.properties")
 */