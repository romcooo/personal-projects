package com.romco.domain.matcher;

public enum TournamentFormat {
    SWISS {
        @Override
        public SwissMatcher buildMatcher() {
            return new SwissMatcher();
        }
    },
    ROUND_ROBIN {
        @Override
        public Matcher buildMatcher() {
            return null;
        }
    },
    SINGLE_ELIMINATION {
        @Override
        public Matcher buildMatcher() {
            return null;
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
