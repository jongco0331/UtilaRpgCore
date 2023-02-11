package com.ut.rpg.core.guis.stat;

import com.ut.rpg.core.guis.GuiBase;
import com.ut.rpg.core.managers.PlayerManager;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.stat.ClickEvent;
import com.ut.rpg.core.objects.stat.DataType;
import com.ut.rpg.core.sounds.Sound;
import com.ut.rpg.core.sounds.stat.StatSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Map;

public class StatGui extends GuiBase {

    public StatGui(Player p) {
        super(p, StatManager.getInstance().getGuiArray().length / 9, "[ URCore ] 스텟");
        Sound.playSound(p, StatSound.OPEN_GUI);

    }

    @Override
    protected void onInvClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if(StatManager.getInstance().getEvents().containsKey(e.getRawSlot()))
        {
            if(p.hasCooldown(Material.BARRIER))
                return;
            ClickEvent events = StatManager.getInstance().getEvents().get(e.getRawSlot());
            if(events == ClickEvent.STAT1)
            {
                StatManager.getInstance().updateData((Player) e.getWhoClicked(), DataType.STAT1);
            }
            if(events == ClickEvent.STAT2)
            {
                StatManager.getInstance().updateData((Player) e.getWhoClicked(), DataType.STAT2);
            }
            if(events == ClickEvent.STAT3)
            {
                StatManager.getInstance().updateData((Player) e.getWhoClicked(), DataType.STAT3);
            }
        }
        refresh();
    }

    @Override
    protected void onInvClose(InventoryCloseEvent e) {
        PlayerManager.getInstance().updatePlayerStatus((Player) e.getPlayer());
    }

    @Override
    protected void onInit() {
        inv.setContents(StatManager.getInstance().getStatGui(p));
    }
}
