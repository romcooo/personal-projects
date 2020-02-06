package com.romco.bracketeer.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PlayerTest {
    
    @Test
    void compareToWithCloseDoubles() {
        Player player1 = new Player(1);
        player1.setScore(1.4d);
        
        Player player2 = new Player(2);
        player2.setScore(0.6d);
        
        assertEquals(1, player1.compareTo(player2));
//        log.info(player1.compareTo(player2))
    }
    
    @Test
    void compareToWithEqualDoubles() {
        Player player1 = new Player(1);
        player1.setScore(1.0d);
    
        Player player2 = new Player(2);
        player2.setScore(1.0d);
    
        assertEquals(0, player1.compareTo(player2));
    }
    
    @Test
    void testEquals() {
        Player player1 = new Player(1);
        player1.setScore(1.0d);
        
        Player player2 = new Player(2);
        player2.setScore(1.0d);
    
        assertEquals(player1, player2);
    }
}