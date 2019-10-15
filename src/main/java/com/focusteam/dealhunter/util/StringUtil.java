package com.focusteam.dealhunter.util;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.Format;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class StringUtil {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String SYMBOL = "!@#$%^&*()_+=-[] \\|';:/.,<>";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER + SYMBOL;
    private static final String DATA_FOR_RANDOM_STRING2 = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    public String randomString(){
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            //System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public String codeSale(int percent){
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING2.length());
            char rndChar = DATA_FOR_RANDOM_STRING2.charAt(rndCharAt);
            sb.append(rndChar);
        }
        String code = sb.toString() + percent;
        return code.toUpperCase();
    }

    public String dateFormatFromLong(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("HH:mm:ss dd MM yyyy");
        return format.format(date);
    }

    public String encryptMD5(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String unAccent(String name){
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^\\p{ASCII}]", "")
                .replace(" ", "-")
                .toLowerCase();
        return name;
    }

    public String longToString(long time){
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

    public long stringToLong(String day){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter3 = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatter4 = new SimpleDateFormat("dd MM yyyy");
        long dayLong = 0;
        if (day.charAt(2) != day.charAt(5)){
            day = day.replace(day.charAt(2), '-').replace(day.charAt(5), '-');
            try {
                dayLong = formatter2.parse(day).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dayLong;
        }else {
            switch (day.charAt(2)){
                case '/' : {
                    try {
                        dayLong = formatter.parse(day).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                } case '-' : {
                    try {
                        dayLong = formatter2.parse(day).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                } case '.' : {
                    try {
                        dayLong = formatter3.parse(day).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                } case ' ' : {
                    try {
                        dayLong = formatter4.parse(day).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                } default:{
                    day = day.replace(day.charAt(2), '/').replace(day.charAt(5), '/');
                    stringToLong(day);
                    break;
                }
            }
        }
        return dayLong;
    }
}
