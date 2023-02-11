package com.ut.rpg.core.utils;

import com.ut.rpg.core.Glow;
import com.ut.rpg.core.exceptions.ItemDataLoadFailedException;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ItemUtil {

    static Glow glow = new Glow(255);

    public static ItemStack getItemStackToYaml(ConfigurationSection section) throws ItemDataLoadFailedException {
        try {
            if(section.get("material") == null)
                return null;
            short data = 0;
            if(section.get("data") != null)
                data = (byte) section.getInt("data");
            int amount = 1;
            if(section.get("amount") != null)
                amount = section.getInt("amount");
            ItemStack stack = new ItemStack(Material.getMaterial(section.getString("material")), amount, data);
            ItemMeta meta = stack.getItemMeta();
            if(section.get("display-name") != null)
                meta.setDisplayName(StringUtil.colorize(section.getString("display-name")));
            if(section.get("lore") != null)
                meta.setLore(StringUtil.colorize(section.getStringList("lore")));
            if(section.get("flags") != null)
            {
                List<String> flags = section.getStringList("flags");
                for(String flag : flags)
                    meta.addItemFlags(ItemFlag.valueOf(flag));
            }
            if(section.get("enchantments") != null)
            {
                List<String> enchantments = section.getStringList("enchantments");
                for(String enchantment : enchantments)
                {
                    String[] split = enchantment.split(":");
                    meta.addEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]), true);
                }
            }
            if(section.get("durability") != null)
                stack.setDurability((short) section.getInt("durability"));
            if(section.get("isUnbreakable") != null && section.getBoolean("isUnbreakable"))
                meta.setUnbreakable(true);
            if(section.get("glowing") != null && section.getBoolean("glowing"))
                meta.addEnchant(glow, 1, true);
            stack.setItemMeta(meta);
            return stack;
        } catch(Exception e) {
            throw new ItemDataLoadFailedException();
        }
    }

    public static String itemToBinary(ItemStack stack)
    {
        YamlConfiguration yaml = new YamlConfiguration();
        net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound tag = new NBTTagCompound();
        nms.save(tag);
        yaml.set("i", tag.toString());
        return DatatypeConverter.printBase64Binary(yaml.saveToString().getBytes(StandardCharsets.UTF_8));
    }

    public static ItemStack binaryToItem(String key)
    {
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.loadFromString(new String(DatatypeConverter.parseBase64Binary(key), StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        try {
            NBTTagCompound comp = MojangsonParser.parse(yaml.getString("i", null));
            net.minecraft.server.v1_12_R1.ItemStack stack = new net.minecraft.server.v1_12_R1.ItemStack(comp);
            return CraftItemStack.asBukkitCopy(stack);
        } catch(MojangsonParseException e) {e.printStackTrace();}
        return null;
    }



}
