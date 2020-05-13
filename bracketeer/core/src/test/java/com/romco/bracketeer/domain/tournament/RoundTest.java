package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.participant.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class RoundTest {
    private static Round testRound;
    private static Match match1, match2;
    private static Participant participant1, participant2, participant3, participant4;

    @BeforeEach
    void setup() {
        participant1 = new Player("player1");
        participant2 = new Player("player2");
        participant3 = new Player("player3");
        participant4 = new Player("player4");
        match1 = new Match(participant1, participant2);
        match1.setMatchNumber(1);
        match2 = new Match(participant3, participant4);
        match2.setMatchNumber(2);
        testRound = new Round();
        testRound.addMatch(match1);
        testRound.addMatch(match2);
    }
    @Test
    void getMatchByParticipant() {
        Match expected = match1;
        Match actual = testRound.getMatchByParticipant(participant1);
        assertEquals(expected, actual);
        actual = testRound.getMatchByParticipant(participant2);
        assertEquals(expected, actual);

        expected = match2;
        actual = testRound.getMatchByParticipant(participant3);
        assertEquals(expected, actual);
        actual = testRound.getMatchByParticipant(participant4);
        assertEquals(expected, actual);
    }

    @Test
    void getMatchByMatchNumber() {
        Match expected = match1;
        Match actual = testRound.getMatchByMatchNumber(1);
        assertEquals(expected, actual);

        expected = match2;
        actual = testRound.getMatchByMatchNumber(2);
        assertEquals(expected, actual);
    }
}