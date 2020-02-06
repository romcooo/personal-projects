package com.romco.bracketeer.model;

public class Main {
    public static void main(String[] args) {
        TournamentImpl tournament = new TournamentImpl(TournamentType.SWISS);
        
        Player player1 = new Player(1, "rom");
        Player player2 = new Player(2, "mar");
        Player player3 = new Player(3, "tom");
        Player player4 = new Player(4, "san");
    
        player1.setScore(1);
        player2.setScore(4);
        player3.setScore(2);
        player4.setScore(4);
        
        tournament.addParticipant(player1);
        tournament.addParticipant(player2);
        tournament.addParticipant(player3);
        tournament.addParticipant(player4);
    
        tournament.generateNextRound();
        /** create tournament:
         * set rules (round robin, swiss, single/double elim)
         * add players
         * get pairings for first round
         */
    
    }
}
