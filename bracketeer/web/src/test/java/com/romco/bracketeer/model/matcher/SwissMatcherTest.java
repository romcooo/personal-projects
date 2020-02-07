package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.participant.Player;
import com.romco.bracketeer.model.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwissMatcherTest {
    
    @Test
    void generateRoundWith8PlayersWithScoresAndOneTie() {
        Player player1 = new Player(1, "rom");
        Player player2 = new Player(2, "mar");
        Player player3 = new Player(3, "tom");
        Player player4 = new Player(4, "san");
        Player player5 = new Player(5, "san");
        Player player6 = new Player(6, "san");
        Player player7 = new Player(7, "san");
        Player player8 = new Player(8, "san");
    
        // note order
        player1.setScore(1);
        player3.setScore(2);
        player4.setScore(4);
        player2.setScore(4);
        player5.setScore(7);
        player6.setScore(8);
        player7.setScore(10);
        player8.setScore(12);
        
        List<Participant> participantList = new ArrayList<>();
        participantList.add(player1);
        participantList.add(player2);
        participantList.add(player3);
        participantList.add(player4);
        participantList.add(player5);
        participantList.add(player6);
        participantList.add(player7);
        participantList.add(player8);
        
        Matcher matcher = new SwissMatcher();
        Round round = matcher.generateRound(participantList);
        
        // first pair - 2 highest scoring
        assertEquals(player8, round.getMatch(0).getParticipants().get(0));
        assertEquals(player7, round.getMatch(0).getParticipants().get(1));
        
        // second pair -
        assertEquals(player6, round.getMatch(1).getParticipants().get(0));
        assertEquals(player5, round.getMatch(1).getParticipants().get(1));
        
        // third pair
        assertEquals(player2, round.getMatch(2).getParticipants().get(0));
        assertEquals(player4, round.getMatch(2).getParticipants().get(1));
        
        // fourth pair
        assertEquals(player3, round.getMatch(3).getParticipants().get(0));
        assertEquals(player1, round.getMatch(3).getParticipants().get(1));
    }
    
    @Test
    void generateRoundWithABye() {
        List<Participant> participantList = new ArrayList<>();
        // 5 participants, ids 0-4
        for (int i = 0; i < 5; i++) {
            Player player = new Player(i, String.valueOf(i));
            player.setScore(i);
            participantList.add(player);
        }
        Matcher matcher = new SwissMatcher();
        Round round = matcher.generateRound(participantList);
        
        assertTrue(round.getMatch(0).isBye());
    }
}