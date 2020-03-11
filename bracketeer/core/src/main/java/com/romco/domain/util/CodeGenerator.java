package com.romco.domain.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class CodeGenerator {
    // TODO
    private static int val = 0;
    private static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lower = upper.toLowerCase();
    private static String digits = "0123456789";
    private static String alphanumeric = upper + lower + digits;
    
    public static String getCode() {
        return getCode(20);
    }

    public static String getCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int l = 0; l < length; l++) {
            sb.append(alphanumeric.charAt(ThreadLocalRandom.current().nextInt(alphanumeric.length())));
        }
        log.debug("string is: {}, length is: {}", alphanumeric, alphanumeric.length());
        return sb.toString();
    }
}
