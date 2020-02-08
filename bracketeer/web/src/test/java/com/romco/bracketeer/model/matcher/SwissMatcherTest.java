package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.Round;
import com.romco.bracketeer.model.Tournament;
import com.romco.bracketeer.model.TournamentImpl;
import com.romco.bracketeer.model.participant.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwissMatcherTest {

    // TODO refactor
    @Test
    void generateRoundWith8PlayersWithScoresAndOneTie() {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        Player player1 = new Player(1, "rom");
        Player player2 = new Player(2, "mar");
        Player player3 = new Player(3, "tom");
        Player player4 = new Player(4, "san");
        Player player5 = new Player(5, "san");
        Player player6 = new Player(6, "san");
        Player player7 = new Player(7, "san");
        Player player8 = new Player(8, "san");
    
        // note order
        tournament.addParticipant(player1);
        tournament.addParticipant(player2);
        tournament.addParticipant(player3);
        tournament.addParticipant(player4);
        tournament.addParticipant(player5);
        tournament.addParticipant(player6);
        tournament.addParticipant(player7);
        tournament.addParticipant(player8);
        
        tournament.setScore(player1, 1);
        tournament.setScore(player3, 2);
        tournament.setScore(player4, 4);
        tournament.setScore(player2, 4);
        tournament.setScore(player5, 7);
        tournament.setScore(player6, 8);
        tournament.setScore(player7, 10);
        tournament.setScore(player8, 12);
        
        Matcher matcher = new SwissMatcher();
        Round round = matcher.generateRound(tournament.getParticipantScores());
        
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
    
//    @Test
//    void generateRoundWithABye() {
//        List<Participant> participantList = new ArrayList<>();
//        // 5 participants, ids 0-4
//        for (int i = 0; i < 5; i++) {
//            Player player = new Player(i, String.valueOf(i));
//            player.setScore(i);
//            participantList.add(player);
//        }
//        Matcher matcher = new SwissMatcher();
//        Round round = matcher.generateRound(participantList);
//
//        assertTrue(round.getMatch(0).isBye());
//    }
}