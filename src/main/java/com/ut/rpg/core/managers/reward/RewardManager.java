package com.ut.rpg.core.managers.reward;

import com.ut.rpg.core.objects.reward.RewardData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class RewardManager {

    private RewardManager() {}

    private static RewardManager rewardManager = new RewardManager();

    public static RewardManager getInstance() {
        return rewardManager;
    }

    @Getter private List<RewardData> rewardData = new ArrayList<>();

    public RewardData getRewardData(String mobName)
    {
        for(RewardData rd : rewardData)
        {
            if(rd.getMobName().equals(mobName))
                return rd;
        }
        return null;
    }


}
