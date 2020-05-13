package com.romco.bracketeer.domain.matcher;

public enum TournamentFormat {
    SWISS {
        @Override
        public Matcher buildMatcher() {
            return new SwissMatcher();
        }
    },
    ROUND_ROBIN {
        @Override
        public Matcher buildMatcher() {
            return new RoundRobinMatcher();
        }
    },
    SINGLE_ELIMINATION {
        @Override
        public Matcher buildMatcher() {
            return new SingleElimMatcher();
        }
    },
    DOUBLE_ELIMINATION {
        @Override
        public Matcher buildMatcher() {
            return null;
        }
    };
    
    public abstract Matcher buildMatcher();
}
