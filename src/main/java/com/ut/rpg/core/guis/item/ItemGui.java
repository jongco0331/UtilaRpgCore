package com.ut.rpg.core.guis.item;

import com.ut.rpg.core.guis.GuiBase;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemGui extends GuiBase {

    private ArrayList<ItemStack> list;

    public ItemGui(Player p) {
        super(p, 6, "[ ItemDB ] 아이템 목록");
    }

    @Override
    protected void onInvClick(InventoryClickEvent e) {

    }

    @Override
    protected void onInvClose(InventoryCloseEvent e) {

    }

    @Override
    protected void onInit() {

    }
}
