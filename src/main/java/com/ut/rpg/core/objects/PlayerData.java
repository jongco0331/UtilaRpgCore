package com.ut.rpg.core.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PlayerData {

    private int point;
    private int stat1;
    private int stat2;
    private int stat3;
    private int level;
    private long exp;
}
