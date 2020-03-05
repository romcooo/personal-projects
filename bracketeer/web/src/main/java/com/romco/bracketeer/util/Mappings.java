package com.romco.bracketeer.util;

public final class Mappings {
    public static final String REDIRECT = "redirect:/";
    
    public static final String HOME = "/templates/index.html";

    
    public static class Tournament {
        public static final String BASE = "tournament";
        public static final String NEW = BASE + "/new";

        public static final String SETUP = BASE + "/setup";

        public static final String ADD_PLAYER = BASE + "/addPlayer";
        public static final String REDIRECT_ADD_PLAYER = REDIRECT + SETUP;

        public static final String REMOVE_PLAYER = BASE + "/removePlayer";
        public static final String REDIRECT_REMOVE_PLAYER = REDIRECT + SETUP;

        public static final String SAVE = NEW + "/save";


//        public static final String EXISTING = BASE + "/existing";
        public static final String EXISTING_WITH_CODE = BASE + "/{tournamentCode}";
        public static final String EXISTING_STANDINGS = EXISTING_WITH_CODE + "/standings";
        public static final String REDIRECT_EXISTING_WITH_CODE = REDIRECT + EXISTING_WITH_CODE;


        public static final String ALL = BASE + "/all";
        public static final String FIND = BASE + "/find";


        public static class Round {
            public static final String BASE = Tournament.EXISTING_WITH_CODE + "/round";
            public static final String WITH_NUMBER = BASE + "/{roundNumber}";
            public static final String REDIRECT_WITH_NUMBER = REDIRECT + WITH_NUMBER;
            public static final String GENERATE = BASE + "/generate";

            public static class Match {
                public static final String BASE = Round.WITH_NUMBER + "/match";
                public static final String WITH_NUMBER = BASE + "/{matchNumber}";
                public static final String RESULT = WITH_NUMBER + "/result";
            }

        }

    }
}
