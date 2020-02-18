package com.romco.bracketeer.util;

public final class ViewNames {
    public static final String HOME = "index";
    
    public static class Tournament {
        public static final String BASE = "tournament/";
        public static final String NEW = BASE + "new";
        
        public static class Test {
            public static final String BASE = Tournament.BASE + "new/";
            public static final String TEST = "test";
        }
    }
}
