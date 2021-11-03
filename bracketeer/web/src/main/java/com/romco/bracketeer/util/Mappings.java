package com.romco.bracketeer.util;

public final class Mappings {
    public static final String REDIRECT_PREFIX = "redirect:/";
    
    public static final String HOME = "/templates/index.html";

    public static final String ABOUT = "/about";

    
    public static class Tournament {
        public static final String BASE = "tournament";


        public static final String NEW = Tournament.BASE + "/new";
        public static final String ALL = BASE + "/all";
        public static final String FIND = BASE + "/find";


        public static final String EXISTING_WITH_CODE = BASE + "/{tournamentCode}";
        public static final String EXISTING_STANDINGS = EXISTING_WITH_CODE + "/standings";
        public static final String REDIRECT_EXISTING_WITH_CODE = REDIRECT_PREFIX + EXISTING_WITH_CODE;


        public static final String SAVE = EXISTING_WITH_CODE + "/save";


        public static final String BRACKET = EXISTING_WITH_CODE + "/bracket";


        public static class Setup {
            public static final String BASE = Tournament.EXISTING_WITH_CODE + "/setup";
            // TODO below is currently named as if it was specific for points, but it also sets best-of
            // TODO also look at how swiss is set
            public static final String SET_POINTS_FOR_MATCH_RESULT_TYPE = BASE + "/rule-set";
            public static final String RESET_POINTS_FOR_ALL_TYPES = SET_POINTS_FOR_MATCH_RESULT_TYPE + "/reset-to-default";
            public static final String REDIRECT_TO_EXISTING_SETUP = REDIRECT_PREFIX + BASE;

            //TODO change to /players POST and /players DELETE?
            public static final String ADD_PLAYER = EXISTING_WITH_CODE + "/addPlayer";
            public static final String REMOVE_PLAYER = EXISTING_WITH_CODE + "/removePlayer";
        }

        public static class Round {
            public static final String BASE = Tournament.EXISTING_WITH_CODE + "/round";
            public static final String WITH_NUMBER = BASE + "/{roundNumber}";
            public static final String REDIRECT_WITH_NUMBER = REDIRECT_PREFIX + WITH_NUMBER;
            public static final String GENERATE = BASE + "/generate";
            public static final String STANDINGS = WITH_NUMBER + "/standings";

            public static final String SAVE_ALL_RESULTS = WITH_NUMBER + "/results/save";


            public static class Match {
                public static final String BASE = Round.WITH_NUMBER + "/match";
                public static final String WITH_NUMBER = BASE + "/{matchNumber}";
                public static final String RESULT = WITH_NUMBER + "/result";
            }

        }

    }


    public static class UserManagement {
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
    }

}
