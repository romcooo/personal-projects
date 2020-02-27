package com.romco.domain.util;

import java.util.Random;

public class CodeGenerator {
    // TODO
    private static int val = 0;
    
    public static String getCode() {
        return Integer.toString(val++);
    }
}
