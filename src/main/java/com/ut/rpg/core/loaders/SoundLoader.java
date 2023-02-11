package com.ut.rpg.core.loaders;

import com.ut.rpg.core.sounds.Sound;
import com.ut.rpg.core.sounds.stat.StatSound;
import com.ut.rpg.core.utils.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SoundLoader implements IDataLoader {


    @Override
    public void load() {
        FileUtil.createFile("stat/sound");

        YamlConfiguration yamlStat = YamlConfiguration.loadConfiguration(new File("plugins/UtilaRpgCore/stat/sound.yml"));
        try {
            StatSound.OPEN_GUI = new Sound(org.bukkit.Sound.valueOf(yamlStat.getString("OPEN_GUI.sound")), (float)yamlStat.getDouble("OPEN_GUI.volume"), (float)yamlStat.getDouble("OPEN_GUI.pitch"));
            StatSound.STAT_UP = new Sound(org.bukkit.Sound.valueOf(yamlStat.getString("STAT_UP.sound")), (float)yamlStat.getDouble("STAT_UP.volume"), (float)yamlStat.getDouble("STAT_UP.pitch"));
            StatSound.NEED_POINT = new Sound(org.bukkit.Sound.valueOf(yamlStat.getString("NEED_POINT.sound")), (float)yamlStat.getDouble("NEED_POINT.volume"), (float)yamlStat.getDouble("NEED_POINT.pitch"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {

    }
}
