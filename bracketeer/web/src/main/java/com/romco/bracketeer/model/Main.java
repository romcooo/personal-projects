package com.romco.bracketeer.model;

import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Player;

public class Main {
    public static void main(String[] args) {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        
        Player player1 = new Player(1, "rom");
        Player player2 = new Player(2, "mar");
        Player player3 = new Player(3, "tom");
        Player player4 = new Player(4, "san");
    
        player1.setScore(0);
        player2.setScore(0);
        player3.setScore(0);
        player4.setScore(0);
        
        tournament.addParticipant(player1);
        tournament.addParticipant(player2);
        tournament.addParticipant(player3);
        tournament.addParticipant(player4);
    
        tournament.generateNextRound();
    
        tournament.setMatchResult(0, player3, 2, 1);
        tournament.setMatchResult(0, player3, 1, 1);
        
        tournament.printStandings();
        
        
        /* create tournament:
         * set rules (round robin, swiss, single/double elim)
         * add players
         * get pairings for first round
         */
    
    }
}
