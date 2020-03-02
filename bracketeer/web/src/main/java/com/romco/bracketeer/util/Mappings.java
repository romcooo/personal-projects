package com.romco.bracketeer.util;

public final class Mappings {
    public static final String REDIRECT = "redirect:/";
    
    public static final String HOME = "/static/index.html";
    
    
    public static class Tournament {
        public static final String BASE = "tournament";
        public static final String NEW = BASE + "/new";

        public static final String ADD_PLAYER = BASE + "/addPlayer";
        public static final String REDIRECT_ADD_PLAYER = REDIRECT + ADD_PLAYER;

        public static final String REMOVE_PLAYER = BASE + "/removePlayer";
        public static final String REDIRECT_REMOVE_PLAYER = REDIRECT + REMOVE_PLAYER;

        public static final String SAVE = NEW + "/save";

        public static final String EXISTING = BASE + "/existing";
        public static final String EXISTING_WITH_CODE = BASE + "/{code}";
        public static final String REDIRECT_EXISTING_WITH_CODE = REDIRECT + EXISTING_WITH_CODE;

        public static final String ALL = BASE + "/all";
        public static final String FIND = BASE + "/find";

        public static class Round {
            public static final String BASE = Tournament.BASE + "/round";
            public static final String WITH_NUMBER = BASE + "/{number}";
            public static final String GENERATE = BASE + "/generate";

        }

    }
}
