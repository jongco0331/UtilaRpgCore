package com.ut.rpg.core.loaders.item;

import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.exceptions.ItemDataLoadFailedException;
import com.ut.rpg.core.hooks.databases.ItemDatabase;
import com.ut.rpg.core.loaders.IDataLoader;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.managers.item.ItemManager;
import com.ut.rpg.core.utils.FileUtil;
import com.ut.rpg.core.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class ItemDataLoader implements IDataLoader {

    @Override
    public void load() {
        ItemManager.getInstance().getIdItemHashMap().clear();
        ItemManager.getInstance().getDbItemHashMap().clear();
        try {
            ItemManager.getInstance().getDbItemHashMap().putAll(ItemDatabase.getInstance().getAllItem());
        } catch (DatabaseHookException e) {
            e.printStackTrace();
        }
        FileUtil.createFolder("ItemData");

        File file = new File("plugins/UtilaRpgCore/ItemData");
        if(file.listFiles() == null)
            return;
        try {

        } catch(Exception e) {}
        for(File f : file.listFiles())
        {
            if(!f.getName().contains(".yml"))
                continue;
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            for(String key : yaml.getKeys(false))
            {
                ConfigurationSection section = yaml.getConfigurationSection(key);
                int id = section.getInt("id");
                try{
                    ItemStack stack = ItemUtil.getItemStackToYaml(section);
                    ItemManager.getInstance().getIdItemHashMap().put(id, stack);
                } catch(ItemDataLoadFailedException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void save() {

    }
}
