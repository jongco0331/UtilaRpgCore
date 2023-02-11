package com.ut.rpg.core.managers.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.TreeMap;

public final class ItemManager {

    private static ItemManager itemManager = new ItemManager();

    private ItemManager() {}

    public static ItemManager getInstance() {
        return itemManager;
    }

    @Getter private TreeMap<Integer, ItemStack> dbItemHashMap = new TreeMap<>();
    @Getter private TreeMap<Integer, ItemStack> idItemHashMap = new TreeMap<>();


}
