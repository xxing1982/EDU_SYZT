package com.syzton.sunread.util;

import java.util.Random;

/**
 * Created by jerry on 3/22/15.
 */
public class NumberUtil {

    public static long generateRandom16(){
       return generateRandom(16);
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }


}
