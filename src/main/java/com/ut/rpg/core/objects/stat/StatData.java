package com.ut.rpg.core.objects.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class StatData {

    private int maxValue;
    private double damage;
    private double health;
    private double defense;
    private double speed;
    private double criticalChance;
    private double criticalDamage;
    private double avoidChance;
}
