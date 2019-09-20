package com.focusteam.dealhunter.util;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.UUID;

public class StringUtil {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String SYMBOL = "!@#$%^&*()_+=-[] \\|';:/.,<>";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER + SYMBOL;
    private static SecureRandom random = new SecureRandom();

    public static String randomString(){
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 20; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            //System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("AACC-" + UUID.randomUUID().toString());
    }
}
