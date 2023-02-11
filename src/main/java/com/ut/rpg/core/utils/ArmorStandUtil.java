package com.ut.rpg.core.utils;

import com.ut.rpg.core.Main;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandUtil {

    public static void spawnStand(int second, Player p, Location loc, long money, long exp)
    {
        List<ArmorStand> armorStands = new ArrayList<>();
        {
            Location copied = loc.clone();
            ArmorStand armorStand = (ArmorStand) p.getLocation().getWorld().spawnEntity(copied.add(0, 2.5, 0), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            armorStand.setInvulnerable(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName("§f[ §e" + p.getName() + " §f]");
            armorStands.add(armorStand);
        }
        {
            Location copied = loc.clone();
            ArmorStand armorStand = (ArmorStand) p.getLocation().getWorld().spawnEntity(copied.add(0, 2.1, 0), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            armorStand.setInvulnerable(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName("§e§l+" + money + " Money");
            armorStands.add(armorStand);
        }
        {
            Location copied = loc.clone();
            ArmorStand armorStand = (ArmorStand) p.getLocation().getWorld().spawnEntity(copied.add(0, 1.9, 0), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setMarker(true);
            armorStand.setInvulnerable(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName("§e§l+" + exp + " Exp");
            armorStands.add(armorStand);
        }


        Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                for(ArmorStand armor : armorStands)
                {
                    armor.remove();
                }
            }
        }, 20 * second);
    }

}
