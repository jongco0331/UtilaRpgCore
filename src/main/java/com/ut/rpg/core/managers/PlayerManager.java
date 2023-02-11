package com.ut.rpg.core.managers;

import com.ut.rpg.core.Lang;
import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.hooks.databases.MoneyDatabase;
import com.ut.rpg.core.hooks.databases.PlayerDataDatabase;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.Account;
import com.ut.rpg.core.objects.PlayerData;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class PlayerManager {

    private static PlayerManager playerManager = new PlayerManager();

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        return playerManager;
    }

    @Getter
    private HashMap<UUID, PlayerData> playerMap = new HashMap<>();
    @Getter
    private HashMap<UUID, Account> playerAccount = new HashMap<>();

    public void loadPlayer(Player p)
    {
        String head = ConfigManager.getInstance().getText_head();
        PlayerDataDatabase dataDatabase = PlayerDataDatabase.getInstance();
        MoneyDatabase moneyDatabase = MoneyDatabase.getInstance();
        p.sendMessage(head + Lang.LOADING_DATA);
        try {
            if(!dataDatabase.isExists(p))
                dataDatabase.insertNewData(p);
            if(!moneyDatabase.isExists(p))
                moneyDatabase.insertNewMoneyData(p);
            playerMap.put(p.getUniqueId(), dataDatabase.getOriginalData(p));
            playerAccount.put(p.getUniqueId(), moneyDatabase.getOriginalData(p));
        } catch (DatabaseHookException e) {
            p.sendMessage(head + Lang.ERROR_LOADING);
            e.printStackTrace();
            return;
        }
        p.sendMessage(head + Lang.LOADING_SUCCESS);
        updatePlayerStatus(p);
    }

    public void updatePlayerStatus(Player p)
    {
        PlayerData pd = playerMap.get(p.getUniqueId());
        StatManager statManager = StatManager.getInstance();
        double health = statManager.getStat1().getHealth() * pd.getStat1()
                + statManager.getStat2().getHealth() * pd.getStat2()
                + statManager.getStat3().getHealth() * pd.getStat3();

        p.setMaxHealth(20 + health);

        double speed = statManager.getStat1().getSpeed() * pd.getStat1()
                + statManager.getStat2().getSpeed() * pd.getStat2()
                + statManager.getStat3().getSpeed() * pd.getStat3();
        p.setWalkSpeed(0.2f + (float) (0.2 * (speed/100)));
        if(p.getHealthScale() != 20)
            p.setHealthScale(20);

    }

    public void updatePlayerExperience(Player p)
    {
        PlayerData pd = playerMap.get(p.getUniqueId());
        int level = pd.getLevel();
        while(true)
        {
            if(pd.getExp() >= StatManager.getInstance().getExpArray().get(pd.getLevel()))
            {
                pd.setExp(Math.abs(StatManager.getInstance().getExpArray().get(pd.getLevel()) - pd.getExp()));
                pd.setLevel(pd.getLevel() + 1);
                continue;
            }
            break;
        }
        if(level != pd.getLevel())
        {
            p.sendMessage(Lang.LEVEL_UP.replaceAll("\\{0\\}", pd.getLevel() + ""));
        }
        p.setLevel(pd.getLevel());
        p.setExp((float)pd.getExp() / (float)StatManager.getInstance().getExpArray().get(pd.getLevel()));
        try {
            PlayerDataDatabase.getInstance().upgradeExpData(p, pd.getLevel(), pd.getExp());
        } catch (DatabaseHookException e) {
            e.printStackTrace();
        }
    }



}
