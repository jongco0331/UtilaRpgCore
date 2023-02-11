package com.ut.rpg.core.loaders.reward;

import com.ut.rpg.core.loaders.IDataLoader;
import com.ut.rpg.core.managers.reward.RewardManager;
import com.ut.rpg.core.objects.reward.RewardData;
import com.ut.rpg.core.utils.FileUtil;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MobSettingLoader implements IDataLoader {

    @Override
    public void load() {
        File file = new File("plugins/UtilaRpgCore/reward");
        if(!file.exists()) {
            file.mkdir();
            FileUtil.createFile("reward/mob-set");
        }
        RewardManager.getInstance().getRewardData().clear();
        if(file.listFiles() == null)
            return;
        for(File f : file.listFiles())
        {
            if(f.getName().contains(".yml"))
            {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                for(String key : yaml.getKeys(false))
                {
                    ConfigurationSection section = yaml.getConfigurationSection(key);
                    String mobName = StringUtil.colorize(key);
                    long money = section.getLong("money");
                    long exp = section.getLong("exp");
                    boolean armorStand = section.getBoolean("show-stand-log");
                    RewardManager.getInstance().getRewardData().add(new RewardData(mobName, money, exp, armorStand));
                }
            }
        }
    }

    @Override
    public void save() {

    }
}
