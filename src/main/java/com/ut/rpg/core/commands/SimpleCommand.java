package com.ut.rpg.core.commands;

import com.ut.rpg.core.Main;
import com.ut.rpg.core.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SimpleCommand implements CommandExecutor {

    Main main = Main.getPlugin();

    boolean canConsoleUse;
    String commandName;
    public String textHead = ConfigManager.getInstance().getText_head();

    public SimpleCommand(boolean canConsoleUse, String commandName)
    {
        this.canConsoleUse = canConsoleUse;
        this.commandName = commandName;
        main.getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!canConsoleUse && !(commandSender instanceof Player))
        {
            commandSender.sendMessage("This Command allows only player.");
            return false;
        }
        execute(commandSender, strings);
        return false;
    }

    public void execute(CommandSender sender, String[] args)
    {

    }

}
