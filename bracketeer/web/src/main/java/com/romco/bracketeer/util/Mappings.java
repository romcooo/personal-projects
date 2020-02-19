package com.romco.bracketeer.util;

public final class Mappings {
    public static final String REDIRECT = "redirect:/";
    
    public static final String HOME = "/static/index.html";
    
    
    public static class Tournament {
        public static final String BASE = "tournament/";
        public static final String NEW = BASE + "new";
        public static final String ADD_PLAYER = BASE + "addPlayer";
        public static final String REDIRECT_ADD_PLAYER = REDIRECT + ADD_PLAYER;
    
        public static class Test {
            public static final String BASE = Tournament.BASE + "new/";
            public static final String TEST = "test";
        }
    }
}
