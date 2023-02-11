package com.ut.rpg.core.objects.stat;

public enum ClickEvent {

    STAT1, STAT2, STAT3;

    public static ClickEvent getEvent(String a)
    {
        if(a.equalsIgnoreCase("STAT1"))
            return STAT1;
        else if(a.equalsIgnoreCase("STAT2"))
            return STAT2;
        else if(a.equalsIgnoreCase("STAT3"))
            return STAT3;
        return null;
    }


}
