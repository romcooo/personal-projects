package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.tournament.Match;
import com.romco.bracketeer.model.tournament.Round;
import com.romco.bracketeer.model.tournament.Tournament;
import com.romco.bracketeer.model.tournament.TournamentImpl;
import com.romco.bracketeer.model.participant.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.anyOf;
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

        Matcher matcher = new SwissMatcher();
        Round round = tournament.generateNextRound();

        List<Match> matches = round.getMatches();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            log.debug("Match #{}: {}", i, match);
        }
        
        // first pair - 2 highest scoring
        assertEquals(player8, round.getMatch(0).getParticipants().get(0));
        assertEquals(player7, round.getMatch(0).getParticipants().get(1));

        // second pair -
        assertEquals(player6, round.getMatch(1).getParticipants().get(0));
        assertEquals(player5, round.getMatch(1).getParticipants().get(1));

        // third pair
        assertEquals(player2, round.getMatch(2).getParticipants().get(0));
        assertThat(round.getMatch(2).getParticipants().get(1)).isIn(player3, player4);

        // fourth pair
        assertThat(round.getMatch(3).getParticipants().get(0)).isIn(player3, player4);
        assertEquals(player1, round.getMatch(3).getParticipants().get(1));
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
        Round round = matcher.generateRound(tournament.getParticipants());

        assertTrue(round.getMatch(0).isBye());
    }
}