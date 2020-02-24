package com.romco.domain;

<<<<<<< HEAD:bracketeer/core/src/main/java/com/romco/domain/Main.java
=======

>>>>>>> be88e0fe0ef736522434cea5e7abdfdce757de22:bracketeer/core/src2/main/java/com/romco/domain/Main.java
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Standings;
import com.romco.domain.tournament.TournamentImpl;

public class Main {
    public static void main(String[] args) {
        TournamentImpl tournament = new TournamentImpl(TournamentFormat.SWISS);
        
        Player player1 = new Player(1, "rom");
        Player player2 = new Player(2, "mar");
        Player player3 = new Player(3, "tom");
        Player player4 = new Player(4, "san");
        Player player5 = new Player(5, "bye");
    
        tournament.setStartingScore(player1, 0);
        tournament.setStartingScore(player2, 0);
        tournament.setStartingScore(player3, 0);
        tournament.setStartingScore(player4, 0);
        tournament.setStartingScore(player5, 0);
        
        tournament.addParticipant(player1);
        tournament.addParticipant(player2);
        tournament.addParticipant(player3);
        tournament.addParticipant(player4);
        tournament.addParticipant(player5);


        tournament.generateNextRound();
    
        tournament.setMatchResult(0, player1, 2, 1);
        tournament.setMatchResult(0, player2, 2, 1);
        tournament.setMatchResult(0, player3, 2, 1);
        tournament.setMatchResult(0, player4, 2, 1);

        Standings.printStandings(tournament);
    
    }
}
