package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.matcher.TournamentFormat;
import com.romco.bracketeer.domain.participant.Participant;
import com.romco.testutil.TestTournamentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        // exactly one will have a score of 9 after tournament, 1 will have score 0, and 3 will have scores 3 and 3 6
        // TODO technically speaking this kind of depends on the TestTournamentBuilder, so revisit this later but
        //  for now it should be fine
        List<Round> rounds = tournament.getRounds();

        for (Round r : rounds) {
            log.debug("Round: {}", r);
            for (Match m : r.getMatches()) {
                log.debug("\tMatch: {}", m);
                for (Participant p : m.getParticipants()) {
                    log.debug("\t\tParticipant: {}, score: {}", p.getName(), p.getScoreAfterRound(r.getRoundNumber()));
                }
            }
        }

        log.info(tournament.getParticipants().toString());
        assertEquals(1, tournament.getParticipants().stream().filter(p -> p.getScore() == 9).count());
        assertEquals(3, tournament.getParticipants().stream().filter(p -> p.getScore() == 6).count());
        assertEquals(3, tournament.getParticipants().stream().filter(p -> p.getScore() == 3).count());
        assertEquals(1, tournament.getParticipants().stream().filter(p -> p.getScore() == 0).count());

        log.debug("Tournament: {}", tournament);
        log.debug("Standings after: {}", Standings.getStandings(tournament, 3));
    }

    @Test
    void generateRoundSize() {
        Round round = tournament.generateRound(1);
        assertEquals(4, round.getMatches().size());
    }
}