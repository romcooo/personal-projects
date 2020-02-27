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

        public static final String EXISTING_WITH_ID = BASE + "/{id}";
        public static final String REDIRECT_EXISTING_WITH_ID = REDIRECT + EXISTING_WITH_ID;
        public static final String EXISTING = BASE + "/existing";
    }
}
