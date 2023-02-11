package com.ut.rpg.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.ut.rpg.core.commands.LoaderCommand;
import com.ut.rpg.core.commands.item.DBItemCommand;
import com.ut.rpg.core.commands.item.ItemCommand;
import com.ut.rpg.core.commands.money.CashCommand;
import com.ut.rpg.core.commands.money.MoneyCommand;
import com.ut.rpg.core.commands.money.control.CashControlCommand;
import com.ut.rpg.core.commands.money.control.MoneyControlCommand;
import com.ut.rpg.core.commands.stat.StatCommand;
import com.ut.rpg.core.events.DefaultListener;
import com.ut.rpg.core.guis.GuiBase;
import com.ut.rpg.core.managers.LoaderManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    private LoaderManager loaderManager;

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        plugin = this;

        registerGlow();

        loaderManager = LoaderManager.getInstance();
        loaderManager.load();

        GuiBase.initGuiBase();

        buildCommand();
        registerEvents();

        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void buildCommand()
    {
        new LoaderCommand();
        new DBItemCommand();
        new ItemCommand();
        new StatCommand();
        new MoneyCommand();
        new CashCommand();
        new MoneyControlCommand();
        new CashControlCommand();
    }

    public void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new DefaultListener(), this);
    }
    @Override
    public void onDisable() {
        loaderManager.save();
    }


    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(255);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
