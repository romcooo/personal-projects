package com.romco.domain.matcher;


import com.romco.domain.participant.Player;
import com.romco.domain.tournament.Match;
import com.romco.domain.tournament.Round;
import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class SwissMatcherTest {

    // TODO refactor
    @Test
    void generateRoundWith8PlayersWithStartingScoresAndOneTie() {
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        Player player1 = new Player(1, "aaron");
        Player player2 = new Player(2, "berenika");
        Player player3 = new Player(3, "cyril");
        Player player4 = new Player(4, "dan");
        Player player5 = new Player(5, "eeriel");
        Player player6 = new Player(6, "fahian");
        Player player7 = new Player(7, "gary");
        Player player8 = new Player(8, "hans");

        // note order
        tournament.addParticipant(player1);
        tournament.addParticipant(player2);
        tournament.addParticipant(player3);
        tournament.addParticipant(player4);
        tournament.addParticipant(player5);
        tournament.addParticipant(player6);
        tournament.addParticipant(player7);
        tournament.addParticipant(player8);

        tournament.setStartingScore(player1, 1);
        tournament.setStartingScore(player3, 2);
        tournament.setStartingScore(player4, 2);
        tournament.setStartingScore(player2, 4);
        tournament.setStartingScore(player5, 7);
        tournament.setStartingScore(player6, 8);
        tournament.setStartingScore(player7, 10);
        tournament.setStartingScore(player8, 12);

        Round round = tournament.generateNextRound();

        List<Match> matches = round.getMatches();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            log.debug("Match #{}: {}", i, match);
        }
        
        // first pair - 2 highest scoring
        assertTrue(round.getMatch(0).getParticipants().contains(player8));
        assertTrue(round.getMatch(0).getParticipants().contains(player7));

        // second pair -
        assertTrue(round.getMatch(1).getParticipants().contains(player6));
        assertTrue(round.getMatch(1).getParticipants().contains(player5));

        // third pair
        assertTrue(round.getMatch(2).getParticipants().contains(player2));
        assertTrue(round.getMatch(2).getParticipants().contains(player3)
                  || round.getMatch(2).getParticipants().contains(player4));

        // fourth pair
        assertTrue(round.getMatch(3).getParticipants().contains(player3)
                           || round.getMatch(3).getParticipants().contains(player4));
        assertTrue(round.getMatch(3).getParticipants().contains(player1));
    }

    // todo fix below
    @Test
    void generateRoundWithABye() {
        // 5 participants, ids 0-4
        Tournament tournament = new TournamentImpl(TournamentFormat.SWISS);
        for (int i = 0; i < 5; i++) {
            Player player = new Player(i, String.valueOf(i));
            tournament.addParticipant(player);
        }
        log.debug("after loop");
        Matcher matcher = new SwissMatcher();
        Round round = tournament.generateNextRound();
//        Round round = matcher.generateRound(tournament.getParticipants());

        assertTrue(round.getMatch(0).isBye());
    }
}