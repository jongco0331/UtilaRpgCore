package com.ut.rpg.core.loaders.stat;

import com.ut.rpg.core.exceptions.ItemDataLoadFailedException;
import com.ut.rpg.core.loaders.IDataLoader;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.stat.ClickEvent;
import com.ut.rpg.core.objects.stat.StatData;
import com.ut.rpg.core.utils.FileUtil;
import com.ut.rpg.core.utils.ItemUtil;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class StatLoader implements IDataLoader {

    @Override
    public void load() {
        FileUtil.createFile("stat/gui");
        FileUtil.createFile("stat/setting");

        YamlConfiguration guiYaml = YamlConfiguration.loadConfiguration(new File("plugins/UtilaRpgCore/stat/gui.yml"));
        YamlConfiguration settingYaml = YamlConfiguration.loadConfiguration(new File("plugins/UtilaRpgCore/stat/setting.yml"));

        StatManager statManager = StatManager.getInstance();
        {
            ConfigurationSection section = settingYaml.getConfigurationSection("STAT1");
            int maxValue = section.getInt("max-value");
            ConfigurationSection abilitySection = section.getConfigurationSection("ability");
            double damage = abilitySection.getDouble("damage");
            double health = abilitySection.getDouble("health");
            double defense = abilitySection.getDouble("defense");
            double speed = abilitySection.getDouble("speed");
            double criticalChance = abilitySection.getDouble("critical-chance");
            double criticalDamage = abilitySection.getDouble("critical-damage");
            double avoidChance = abilitySection.getDouble("avoid-chance");
            statManager.setStat1(new StatData(maxValue, damage, health, defense, speed, criticalChance
                    , criticalDamage, avoidChance));
        }
        {
            ConfigurationSection section = settingYaml.getConfigurationSection("STAT2");
            int maxValue = section.getInt("max-value");
            ConfigurationSection abilitySection = section.getConfigurationSection("ability");
            double damage = abilitySection.getDouble("damage");
            double health = abilitySection.getDouble("health");
            double defense = abilitySection.getDouble("defense");
            double speed = abilitySection.getDouble("speed");
            double criticalChance = abilitySection.getDouble("critical-chance");
            double criticalDamage = abilitySection.getDouble("critical-damage");
            double avoidChance = abilitySection.getDouble("avoid-chance");
            statManager.setStat2(new StatData(maxValue, damage, health, defense, speed, criticalChance
                    , criticalDamage, avoidChance));
        }
        {
            ConfigurationSection section = settingYaml.getConfigurationSection("STAT3");
            int maxValue = section.getInt("max-value");
            ConfigurationSection abilitySection = section.getConfigurationSection("ability");
            double damage = abilitySection.getDouble("damage");
            double health = abilitySection.getDouble("health");
            double defense = abilitySection.getDouble("defense");
            double speed = abilitySection.getDouble("speed");
            double criticalChance = abilitySection.getDouble("critical-chance");
            double criticalDamage = abilitySection.getDouble("critical-damage");
            double avoidChance = abilitySection.getDouble("avoid-chance");
            statManager.setStat3(new StatData(maxValue, damage, health, defense, speed, criticalChance
                    , criticalDamage, avoidChance));
        }

        ConfigurationSection expSection = settingYaml.getConfigurationSection("exp");
        int startLevel = expSection.getInt("start-level");
        int maxLevel = expSection.getInt("max-level");
        int startPoint = expSection.getInt("start-point");
        int bonusPoint = expSection.getInt("bonus-point");
        HashMap<Integer, Integer> expArray = new HashMap<>();
        ConfigurationSection expArraySection = expSection.getConfigurationSection("exp-array");
        for(String key : expArraySection.getKeys(false))
        {
            if(StringUtil.isInteger(key))
                expArray.put(Integer.parseInt(key), expArraySection.getInt(key));
        }
        statManager.updateExpInfo(startLevel, maxLevel, startPoint, bonusPoint, expArray);

        String[] guiArray = guiYaml.getString("guiArray").split(" ");
        HashMap<String, ItemStack> components = new HashMap<>();
        ConfigurationSection componentsSection = guiYaml.getConfigurationSection("Components");
        for(String key : componentsSection.getKeys(false))
        {
            try {
                components.put(key, ItemUtil.getItemStackToYaml(componentsSection.getConfigurationSection(key)));
            } catch (ItemDataLoadFailedException e) {
                e.printStackTrace();
            }
        }

        ConfigurationSection eventsSection = guiYaml.getConfigurationSection("Events");
        HashMap<Integer, ClickEvent> events = new HashMap<>();
        for(String key : eventsSection.getKeys(false))
        {
            if(StringUtil.isInteger(key))
                events.put(Integer.parseInt(key), ClickEvent.getEvent(eventsSection.getString(key)));
        }

        statManager.updateGui(guiArray, components, events);

    }

    @Override
    public void save() {

    }
}
