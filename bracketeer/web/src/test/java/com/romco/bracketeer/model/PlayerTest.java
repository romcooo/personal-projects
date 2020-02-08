package com.romco.bracketeer.model;

import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PlayerTest {
    
    @Test
    void compareToWithCloseDoubles() {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        Player player1 = new Player(1);
        tournament.addParticipant(player1);
        tournament.setScore(player1, 1.4d);

        Player player2 = new Player(2);
        tournament.addParticipant(player2);
        tournament.setScore(player2, 0.6d);

        assertEquals(1, player1.compareTo(player2));
//        log.info(player1.compareTo(player2))
    }
    
    @Test
    void compareToWithEqualDoubles() {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        Player player1 = new Player(1);
        tournament.addParticipant(player1);
        tournament.setScore(player1, 1.0d);
    
        Player player2 = new Player(2);
        tournament.addParticipant(player2);
        tournament.setScore(player2, 1.0d);
    
        assertEquals(0, player1.compareTo(player2));
    }
    
    @Test
    void testEquals() {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);

        Player player1 = new Player(1);
        tournament.addParticipant(player1);
        tournament.setScore(player1, 1.0d);
        
        Player player2 = new Player(2);
        tournament.addParticipant(player2);
        tournament.setScore(player2, 1.0d);
    
        assertEquals(player1, player2);
    }
}