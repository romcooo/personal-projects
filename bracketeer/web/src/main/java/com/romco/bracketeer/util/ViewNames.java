package com.romco.bracketeer.util;

public final class ViewNames {
    public static final String REDIRECT = "redirect:/";

    public static final String HOME = "index";

    public static final String ABOUT = "other/about";

    public static final String ERROR_WITH_MESSAGE = "error-with-message";

    public static class Tournament {
        private Tournament() { }

        public static final String BASE = "tournament";

        public static final String NEW = BASE + "/new";

        public static final String SETUP = BASE + "/setup";

        public static final String STANDINGS = BASE + "/standings";

        public static final String ROUND = BASE + "/round";

        public static final String BRACKET_VISUALISATION = BASE + "/bracket-visualisation";

        public static final String ALL = BASE + "/all";

        public static final String FIND = BASE + "/find";
    }

    public static class UserManagement {

        private UserManagement() { }

        public static final String BASE = "user-management";

        public static final String LOGIN = BASE + "/login";

        public static final String REGISTER = BASE + "/register";
    }
}
