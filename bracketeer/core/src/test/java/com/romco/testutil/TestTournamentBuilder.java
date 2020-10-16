package com.romco.testutil;

import com.romco.bracketeer.domain.matcher.TournamentFormat;
import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.participant.Player;
import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.Tournament;
import com.romco.bracketeer.domain.tournament.TournamentImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestTournamentBuilder {
    
    public enum DummyScoreType {
        ALL_START_WITH_0 {
            @Override
            public double getNextScore() {
                return 0;
            }
        },
        ASCENDING_SCORES {
            private double i = 0;
            @Override
            public double getNextScore() {
                return i++;
            }
        },
        DESCENDING_SCORES {
            private double i = 30;
            @Override
            public double getNextScore() {
                return i--;
            }
        },
        ASC_WITH_TIE {
            private double i = 0;
            @Override
            public double getNextScore() {
                return i++/2;
            }
        },
        DESC_WITH_TIE {
            private double i = 30;
            @Override
            public double getNextScore() {
                return i--/2;
            }
        };
        public abstract double getNextScore();
    }
    
    static class NameGenerator {
        public static final String[] alphabeticalNames = {
                "Aaron", "Berenika", "Cyril", "Derek", "Ezreal", "Finn", "Gary", "Hilda", "Ilya", "Jekyll",
                "Kilian", "Larry", "Monroe", "Nate", "Orepheus", "Pate", "Roren", "Singed", "Taliyah"};
        public static String getName(int i) {
            if (alphabeticalNames.length > i) {
                return alphabeticalNames[i];
            }
            else return "ZZUnavailable";
        }
    }
    
    public static Tournament build(int rounds,
                                   int players,
                                   DummyScoreType dummyScoreType,
                                   TournamentFormat format) {
        Tournament tournament = new TournamentImpl(format);
        for (int p = 0; p < players; p++) {
            Participant participant = new Player(p, NameGenerator.getName(p));
            tournament.addParticipant(participant);
            tournament.setStartingScore(participant, dummyScoreType.getNextScore());
        }
    
        for (int r = 1; r <= rounds; r++) {
            tournament.generateNextRound();
            // TODO here is likely an issue
            List<Match> matches = tournament.getRound(r).getMatches();
            for (Match match : matches) {
                Map<Participant, Integer> results = new HashMap<>();
                results.put(match.getParticipants().get(0), 2);
                results.put(match.getParticipants().get(1), 1);
                log.info("setting result in round {}: {}", r, results);
                match.setMatchScore(results);
            }
        }

        return tournament;
    }
}
