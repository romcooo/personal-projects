package com.romco.testutil;

import com.romco.domain.tournament.Tournament;
import com.romco.domain.tournament.TournamentImpl;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.participant.Player;


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
                "Kilian", "Larry", "Monroe", "Nate", "Orepheus", "Pate", "Roren", "Singed", "Talyia"};
        public static String getName(int i) {
            if (alphabeticalNames.length > i) {
                return alphabeticalNames[i];
            }
            else return "ZZUnavailable";
        }
    }
    
    public static Tournament build(int rounds, int players, DummyScoreType dummyScoreType, TournamentFormat format) {
        Tournament tournament = new TournamentImpl(format);
        for (int p = 0; p < players; p++) {
            Participant participant = new Player(p, NameGenerator.getName(p));
            tournament.addParticipant(participant);
            tournament.setStartingScore(participant, dummyScoreType.getNextScore());
        }
    
        for (int r = 1; r <= rounds; r++) {
            tournament.generateNextRound();
            for (int p = 0; p < players; p++) {
                tournament.setMatchResult(r, tournament.getParticipants().get(p), 2, 1);
            }
        }
        
        return tournament;
    }
}
