package com.ut.rpg.core.utils;

import java.util.Random;

public class RandomUtil {

    public static boolean chanceOf(int chance)
    {
        int value = new Random().nextInt(100);
        return value < chance;
    }


}
