package com.ut.rpg.core.events;

import com.ut.rpg.core.Lang;
import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.hooks.databases.MoneyDatabase;
import com.ut.rpg.core.managers.ConfigManager;
import com.ut.rpg.core.managers.PlayerManager;
import com.ut.rpg.core.managers.reward.RewardManager;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.Account;
import com.ut.rpg.core.objects.PlayerData;
import com.ut.rpg.core.objects.reward.RewardData;
import com.ut.rpg.core.utils.ArmorStandUtil;
import com.ut.rpg.core.utils.RandomUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        PlayerManager.getInstance().loadPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent e)
    {
        Player p = e.getEntity().getKiller();
        if(p == null)
            return;
        if(e.getEntity().getCustomName() == null)
            return;
        RewardData data = RewardManager.getInstance().getRewardData(e.getEntity().getCustomName());
        if(data == null)
            return;
        PlayerData pd = PlayerManager.getInstance().getPlayerMap().get(p.getUniqueId());
        Account account = PlayerManager.getInstance().getPlayerAccount().get(p.getUniqueId());
        pd.setExp(pd.getExp() + data.getExp());
        PlayerManager.getInstance().updatePlayerExperience(p);
        account.setMoney(account.getMoney() + data.getMoney());
        try {
            MoneyDatabase.getInstance().upgradeAccountMoneyData(p, account.getMoney());
        } catch (DatabaseHookException ex) {
            ex.printStackTrace();
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Lang.GET_EXP_ACTIONBAR.replaceAll("\\{0\\}", data.getExp() + "")));
        if(data.isArmorStand())
        {
            ArmorStandUtil.spawnStand(4, p, e.getEntity().getLocation(), data.getMoney(), data.getExp());
        }
    }

    @EventHandler
    public void onExp(PlayerExpChangeEvent e)
    {
        e.setAmount(0);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player p = (Player) e.getDamager();
            PlayerData pd = PlayerManager.getInstance().getPlayerMap().get(p.getUniqueId());
            int stat1 = pd.getStat1();
            int stat2 = pd.getStat2();
            int stat3 = pd.getStat3();

            StatManager statManager = StatManager.getInstance();

            double damage = (int) (statManager.getStat1().getDamage() * stat1
                                + statManager.getStat2().getDamage() * stat2
                                + statManager.getStat3().getDamage() * stat3);

            int criChance = (int) (statManager.getStat1().getCriticalChance() * stat1
                                + statManager.getStat2().getCriticalChance() * stat2
                                + statManager.getStat3().getCriticalChance() * stat3);
            if(RandomUtil.chanceOf(criChance))
            {
                int criDamage = (int) (statManager.getStat1().getCriticalDamage() * stat1
                                        + statManager.getStat2().getCriticalDamage() * stat2
                                        + statManager.getStat3().getCriticalDamage() * stat3);
                damage += (int)(damage * ((criDamage+50.0)/100.0));
                p.sendMessage(ConfigManager.getInstance().getText_head() + Lang.CRITICAL_MESSAGE);
            }

            e.setDamage(damage);
        }
        if(e.getEntity() instanceof Player)
        {
            Player victim = (Player) e.getEntity();
            PlayerData pd = PlayerManager.getInstance().getPlayerMap().get(victim.getUniqueId());
            int stat1 = pd.getStat1();
            int stat2 = pd.getStat2();
            int stat3 = pd.getStat3();

            StatManager statManager = StatManager.getInstance();

            int avoidingChance = (int) (statManager.getStat1().getAvoidChance() * stat1
                                + statManager.getStat2().getAvoidChance() * stat2
                                + statManager.getStat3().getAvoidChance() * stat3);

            if(RandomUtil.chanceOf(avoidingChance))
            {
                victim.sendMessage(ConfigManager.getInstance().getText_head() + Lang.AVOIDING_MESSAGE);
                e.setCancelled(true);
            }

            double damage = e.getDamage();

            int defense = (int) (statManager.getStat1().getDefense() * stat1
                                + statManager.getStat2().getDefense() * stat2
                                + statManager.getStat3().getDefense() * stat3);

            damage -= (int)(damage * (defense/100.0));

            if(damage < 0)
                damage = 0;

            e.setDamage(damage);

        }
    }

    /* Damage Indicator */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntity(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player p = (Player) e.getDamager();
            p.sendTitle("", "                       §c§o+" + (int)e.getDamage() + " §cdamage", 5, 20, 5);
        }
    }

    @EventHandler
    public void onTab(TabCompleteEvent e)
    {
        if(e.getSender().isOp())
            return;
        List<String> completions = Arrays.asList(new String[]{"/스텟", "/돈", "/캐시"});
        e.getSender().sendMessage(e.getBuffer());
        e.setCompletions(completions);

        List<String> complete = completions;
        for(String a : complete)
        {
            if(!a.startsWith("/" + e.getBuffer()))
                complete.remove(a);
        }
        e.setCompletions(complete);
    }

}
