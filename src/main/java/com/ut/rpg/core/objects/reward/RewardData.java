package com.ut.rpg.core.objects.reward;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RewardData {

    private String mobName;
    private long money;
    private long exp;
    private boolean armorStand;

}
