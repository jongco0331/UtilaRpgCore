package com.ut.rpg.core.loaders;

import com.ut.rpg.core.Lang;
import com.ut.rpg.core.managers.ConfigManager;
import com.ut.rpg.core.utils.FileUtil;
import com.ut.rpg.core.utils.StringUtil;

public class ConfigLoader implements IDataLoader {

    @Override
    public void load() {
        FileUtil.createFile("config");
        ConfigManager.getInstance().setText_head(StringUtil.colorize(plugin.getConfig().getString("text-head")));
        Lang.update();
    }

    @Override
    public void save() {

    }
}
