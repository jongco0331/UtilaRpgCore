package com.ut.rpg.core.utils;

import java.util.List;

public class StringUtil {

    public static String colorize(String original)
    {
        if(original != null)
            return original.replaceAll("&", "§");
        return null;
    }

    public static List<String> colorize(List<String> originals)
    {
        if(originals != null)
        {
            for(int i = 0; i < originals.size(); i++)
            {
                originals.set(i, colorize(originals.get(i)));
            }
            return originals;
        }
        return null;
    }

    public static boolean isInteger(String str)
    {
        if(str.matches("-?\\d+"))
            return true;
        return false;
    }


}
