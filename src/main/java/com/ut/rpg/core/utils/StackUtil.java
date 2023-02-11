package com.ut.rpg.core.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StackUtil {

    public static ItemStack getStack(Material material, String display_name)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtil.colorize(display_name));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, String display_name, List<String> lore)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(StringUtil.colorize(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, String display_name, List<String> lore, short durability)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtil.colorize(display_name));
        if(lore != null)
            meta.setLore(StringUtil.colorize(lore));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        stack.setDurability(durability);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, String display_name, short durability)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtil.colorize(display_name));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        stack.setDurability(durability);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, short durability)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        stack.setDurability(durability);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, String display_name, List<String> lore, byte data)
    {
        ItemStack stack = new ItemStack(material, 1, data);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtil.colorize(display_name));
        meta.setLore(StringUtil.colorize(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getStack(Material material, String display_name, byte data)
    {
        ItemStack stack = new ItemStack(material, 1, data);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(StringUtil.colorize(display_name));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }
}
