package com.ut.rpg.core.loaders;

import com.ut.rpg.core.Main;

public interface IDataLoader {

    Main plugin = Main.getPlugin();

    void load();

    void save();

}
