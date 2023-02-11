package com.ut.rpg.core.managers;

import com.ut.rpg.core.loaders.ConfigLoader;
import com.ut.rpg.core.loaders.DBLoader;
import com.ut.rpg.core.loaders.IDataLoader;
import com.ut.rpg.core.loaders.SoundLoader;
import com.ut.rpg.core.loaders.item.ItemDataLoader;
import com.ut.rpg.core.loaders.reward.MobSettingLoader;
import com.ut.rpg.core.loaders.stat.StatLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class LoaderManager {

    private static LoaderManager loaderManager = new LoaderManager();

    private LoaderManager() {}

    public static LoaderManager getInstance() {
        return loaderManager;
    }

    @Getter
    private List<IDataLoader> loaders = new ArrayList<>();

    public void load()
    {
        loaders.add(new DBLoader());
        loaders.add(new ConfigLoader());
        loaders.add(new ItemDataLoader());
        loaders.add(new StatLoader());
        loaders.add(new SoundLoader());
        loaders.add(new MobSettingLoader());

        for(IDataLoader loader : loaders)
        {
            loader.load();
        }

        for(Player p : Bukkit.getOnlinePlayers())
        {
            PlayerManager.getInstance().loadPlayer(p);
        }
    }

    public void save()
    {
        for(IDataLoader loader : loaders)
        {
            loader.save();
        }
    }

}
