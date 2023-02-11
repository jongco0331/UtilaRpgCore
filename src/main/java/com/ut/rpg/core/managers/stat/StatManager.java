package com.ut.rpg.core.managers.stat;

import com.ut.rpg.core.Lang;
import com.ut.rpg.core.Main;
import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.hooks.databases.PlayerDataDatabase;
import com.ut.rpg.core.managers.ConfigManager;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.managers.PlayerManager;
import com.ut.rpg.core.objects.PlayerData;
import com.ut.rpg.core.objects.stat.ClickEvent;
import com.ut.rpg.core.objects.stat.DataType;
import com.ut.rpg.core.objects.stat.StatData;
import com.ut.rpg.core.sounds.Sound;
import com.ut.rpg.core.sounds.stat.StatSound;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class StatManager {

    private StatManager() {}

    private static StatManager statManager = new StatManager();

    public static StatManager getInstance() {
        return statManager;
    }


    @Getter @Setter private StatData stat1;
    @Getter @Setter private StatData stat2;
    @Getter @Setter private StatData stat3;

    @Getter private int startLevel;
    @Getter private int maxLevel;
    @Getter private int startPoint;
    @Getter private int bonusPoint;
    @Getter private HashMap<Integer, Integer> expArray;

    @Getter private String[] guiArray;
    @Getter private HashMap<String, ItemStack> components;
    @Getter private HashMap<Integer, ClickEvent> events;

    public void updateExpInfo(int startLevel, int maxLevel, int startPoint, int bonusPoint, HashMap<Integer, Integer> expArray)
    {
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
        this.startPoint = startPoint;
        this.bonusPoint = bonusPoint;
        this.expArray = expArray;
    }

    public void updateGui(String[] guiArray, HashMap<String, ItemStack> components, HashMap<Integer, ClickEvent> events)
    {
        this.guiArray = guiArray;
        this.components = components;
        this.events = events;
    }

    public void updateData(Player p, DataType data)
    {
        PlayerData playerData = PlayerManager.getInstance().getPlayerMap().get(p.getUniqueId());
        if(data == DataType.STAT1)
        {
            if(playerData.getPoint() < 1) {
                Sound.playSound(p, StatSound.NEED_POINT);
                p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.NEED_STATPOINT);
                return;
            }
            playerData.setPoint(playerData.getPoint() - 1);
            playerData.setStat1(playerData.getStat1() + 1);
            Sound.playSound(p, StatSound.STAT_UP);
            p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.UP_STAT1);
            try {
                PlayerDataDatabase.getInstance().upgradeStatData(p, "stat1", playerData.getPoint(), playerData.getStat1());
            } catch (DatabaseHookException e) {
                e.printStackTrace();
            }
        }
        if(data == DataType.STAT2)
        {
            if(playerData.getPoint() < 1) {
                Sound.playSound(p, StatSound.NEED_POINT);
                p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.NEED_STATPOINT);
                return;
            }
            playerData.setPoint(playerData.getPoint() - 1);
            playerData.setStat1(playerData.getStat2() + 1);
            Sound.playSound(p, StatSound.STAT_UP);
            p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.UP_STAT2);
            try {
                PlayerDataDatabase.getInstance().upgradeStatData(p, "stat2", playerData.getPoint(), playerData.getStat2());
            } catch (DatabaseHookException e) {
                e.printStackTrace();
            }
        }
        if(data == DataType.STAT3)
        {
            if(playerData.getPoint() < 1) {
                Sound.playSound(p, StatSound.NEED_POINT);
                p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.NEED_STATPOINT);
                return;
            }
            playerData.setPoint(playerData.getPoint() - 1);
            playerData.setStat1(playerData.getStat3() + 1);
            Sound.playSound(p, StatSound.STAT_UP);
            p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.UP_STAT3);
            try {
                PlayerDataDatabase.getInstance().upgradeStatData(p, "stat3", playerData.getPoint(), playerData.getStat3());
            } catch (DatabaseHookException e) {
                e.printStackTrace();
            }
        }
        p.setCooldown(Material.BARRIER, 5);
    }

    public ItemStack[] getStatGui(Player p)
    {
        HashMap<UUID, PlayerData> playerMap = PlayerManager.getInstance().getPlayerMap();
        if(!playerMap.containsKey(p.getUniqueId()))
        {
            p.sendMessage("§c플레이어 데이터가 존재하지 않습니다.");
            return null;
        }
        Inventory inv = Bukkit.createInventory(null, guiArray.length, "[ URCore ] 스텟");
        PlayerData playerData = playerMap.get(p.getUniqueId());

        HashMap<String, ItemStack> migrationComponents = new HashMap<>();
        for(Map.Entry<String, ItemStack> entry : components.entrySet())
        {
            migrationComponents.put(entry.getKey(), getParsingItem(p, entry.getValue(), playerData));
        }

        int i = 0;
        for(String stack : guiArray)
        {
            inv.setItem(i, migrationComponents.get(stack));
            i++;
        }

        return inv.getContents();
    }

    protected ItemStack getParsingItem(Player p, ItemStack stack, PlayerData playerData)
    {
        if(stack == null)
            return null;
        ItemStack copied = stack.clone();
        if(!copied.hasItemMeta())
            return stack;
        ItemMeta meta = copied.getItemMeta();
        if(meta.hasDisplayName())
            meta.setDisplayName(meta.getDisplayName().replaceAll("<player>", p.getName())
                    .replaceAll("<damage>", (stat1.getDamage() * playerData.getStat1() + stat2.getDamage() * playerData.getStat2() + stat3.getDamage() * playerData.getStat3()) + "")
                    .replaceAll("<health>", (stat1.getHealth() * playerData.getStat1() + stat2.getHealth() * playerData.getStat2() + stat3.getHealth() * playerData.getStat3()) + "")
                    .replaceAll("<defense>", (stat1.getDefense() * playerData.getStat1() + stat2.getDefense() * playerData.getStat2() + stat3.getDefense() * playerData.getStat3()) + "")
                    .replaceAll("<speed>", (stat1.getSpeed() * playerData.getStat1() + stat2.getSpeed() * playerData.getStat2() + stat3.getSpeed() * playerData.getStat3()) + "")
                    .replaceAll("<critical_chance>", (stat1.getCriticalChance() * playerData.getStat1() + stat2.getCriticalChance() * playerData.getStat2() + stat3.getCriticalChance() * playerData.getStat3()) + "")
                    .replaceAll("<critical_damage>", (stat1.getCriticalDamage() * playerData.getStat1() + stat2.getCriticalDamage() * playerData.getStat2() + stat3.getCriticalDamage() * playerData.getStat3()) + "")
                    .replaceAll("<avoid_chance>", (stat1.getAvoidChance() * playerData.getStat1() + stat2.getAvoidChance() * playerData.getStat2() + stat3.getAvoidChance() * playerData.getStat3()) + "")
                    .replaceAll("<point>", playerData.getPoint() + "")
                    .replaceAll("<stat1_point>", playerData.getStat1() + "")
                    .replaceAll("<stat1_max>", statManager.getStat1().getMaxValue() + "")
                    .replaceAll("<stat2_point>", playerData.getStat2() + "")
                    .replaceAll("<stat2_max>", statManager.getStat2().getMaxValue() + "")
                    .replaceAll("<stat3_point>", playerData.getStat3() + "")
                    .replaceAll("<stat3_max>", statManager.getStat3().getMaxValue() + "")
            );
        if(meta.hasLore())
        {
            List<String> lore = meta.getLore();
            for(int i = 0; i < lore.size(); i++)
            {
                lore.set(i, lore.get(i).replaceAll("<player>", p.getName())
                        .replaceAll("<damage>", (stat1.getDamage() * playerData.getStat1() + stat2.getDamage() * playerData.getStat2() + stat3.getDamage() * playerData.getStat3()) + "")
                        .replaceAll("<health>", (stat1.getHealth() * playerData.getStat1() + stat2.getHealth() * playerData.getStat2() + stat3.getHealth() * playerData.getStat3()) + "")
                        .replaceAll("<defense>", (stat1.getDefense() * playerData.getStat1() + stat2.getDefense() * playerData.getStat2() + stat3.getDefense() * playerData.getStat3()) + "")
                        .replaceAll("<speed>", (stat1.getSpeed() * playerData.getStat1() + stat2.getSpeed() * playerData.getStat2() + stat3.getSpeed() * playerData.getStat3()) + "")
                        .replaceAll("<critical_chance>", (stat1.getCriticalChance() * playerData.getStat1() + stat2.getCriticalChance() * playerData.getStat2() + stat3.getCriticalChance() * playerData.getStat3()) + "")
                        .replaceAll("<critical_damage>", (stat1.getCriticalDamage() * playerData.getStat1() + stat2.getCriticalDamage() * playerData.getStat2() + stat3.getCriticalDamage() * playerData.getStat3()) + "")
                        .replaceAll("<avoid_chance>", (stat1.getAvoidChance() * playerData.getStat1() + stat2.getAvoidChance() * playerData.getStat2() + stat3.getAvoidChance() * playerData.getStat3()) + "")
                        .replaceAll("<point>", playerData.getPoint() + "")
                        .replaceAll("<stat1_point>", playerData.getStat1() + "")
                        .replaceAll("<stat1_max>", statManager.getStat1().getMaxValue() + "")
                        .replaceAll("<stat2_point>", playerData.getStat2() + "")
                        .replaceAll("<stat2_max>", statManager.getStat2().getMaxValue() + "")
                        .replaceAll("<stat3_point>", playerData.getStat3() + "")
                        .replaceAll("<stat3_max>", statManager.getStat3().getMaxValue() + "")
                );
            }
            meta.setLore(lore);
        }
        copied.setItemMeta(meta);
        return copied;
    }

}
