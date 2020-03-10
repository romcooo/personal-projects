package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class MatchTest {
    private static Match testMatch;
    private static Participant participant1;
    private static Participant participant2;

    @BeforeEach
    void setup() {
        participant1 = new Player("player1");
        participant2 = new Player("player2");

        testMatch = new Match();
        testMatch.addParticipant(participant1);
        testMatch.addParticipant(participant2);

    }

    @Test
    void getOther() {
        Participant actual = testMatch.getOther(participant1);
        Participant expected = participant2;
        assertEquals(actual, expected);
    }

    @Test
    void setMatchScoreOnlyGamesWon() {
        List<MatchResult> actual;
        actual = testMatch.setMatchScore(participant1, 2);
        assertEquals(1, actual.size());
        assertEquals(actual.get(0).getForParticipant(), participant1);
        assertEquals(actual.get(0).getGamesWon(), 2);
        assertEquals(actual.get(0).getOfMatch(), testMatch);
    }

    @Test
    void setMatchScoreGamesWonAndLost() {
        List<MatchResult> actual;
        actual = testMatch.setMatchScore(participant1, 2,1);
        assertEquals(2, actual.size());

        assertEquals(actual.get(0).getForParticipant(), participant1);
        assertEquals(actual.get(0).getGamesWon(), 2);
        assertEquals(actual.get(0).getOfMatch(), testMatch);

        assertEquals(actual.get(1).getForParticipant(), participant2);
        assertEquals(actual.get(1).getGamesWon(), 1);
        assertEquals(actual.get(1).getOfMatch(), testMatch);
    }

    @Test
    void getMatchResultWinLoss() {
        testMatch.setMatchScore(participant1, 2, 1);
        assertEquals(testMatch.getMatchResult(participant1), MatchResultEnum.WIN);
        assertEquals(testMatch.getMatchResult(participant2), MatchResultEnum.LOSS);
    }

    @Test
    void getMatchResultTie() {
        testMatch.setMatchScore(participant1, 1, 1);
        assertEquals(testMatch.getMatchResult(participant1), MatchResultEnum.TIE);
        assertEquals(testMatch.getMatchResult(participant2), MatchResultEnum.TIE);
    }
}