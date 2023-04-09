package com.peigo.msvcaccounts.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
    public static String getTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public static String generateBankAccount() {
        Random rand = new Random();
        String value;
        String result;
        int numberRand = rand.nextInt(100000000);
        value = "0011-0426-";
        result = value.concat(String.valueOf(numberRand));
        return result;
    }

    public static String generateNumberOperarion() {
        String result;
        Random rand = new Random();
        int numberRand = rand.nextInt(100000000);
        result = String.valueOf(numberRand);
        return result;
    }
}
