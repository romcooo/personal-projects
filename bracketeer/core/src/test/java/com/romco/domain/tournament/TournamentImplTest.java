package com.romco.domain.tournament;

import com.romco.domain.matcher.TournamentFormat;
import com.romco.testutil.TestTournamentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TournamentImplTest {
    Tournament tournament;

    @BeforeEach
    void setup() {
        tournament = TestTournamentBuilder.build(3,
                                                 8,
//                                                 TestTournamentBuilder.DummyScoreType.ASCENDING_SCORES, TODO check this bug
                                                 TestTournamentBuilder.DummyScoreType.ALL_START_WITH_0,
                                                 TournamentFormat.SWISS);
    }

    @Test
    void generateRound() {
        Round round = tournament.generateRound(1);
        assertEquals(4, round.getMatches().size());
        // exactly one will have a score of 9 after tournament
        // TODO technically speaking this kind of depends on the TestTournamentBuilder, so revisit this later but
        //  for now it should be fine
        assertEquals(1, tournament.getParticipants().stream().filter(p -> p.getScore() == 9).count());
        assertEquals(3, tournament.getParticipants().stream().filter(p -> p.getScore() == 6).count());
        assertEquals(3, tournament.getParticipants().stream().filter(p -> p.getScore() == 3).count());
        assertEquals(1, tournament.getParticipants().stream().filter(p -> p.getScore() == 0).count());

        log.debug("Tournament: {}", tournament);
        log.debug("Standings after: {}", Standings.getStandings(tournament, 3));
    }
}