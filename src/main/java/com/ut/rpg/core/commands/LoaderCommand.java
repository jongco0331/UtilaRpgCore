package com.ut.rpg.core.commands;

import com.ut.rpg.core.managers.LoaderManager;
import org.bukkit.command.CommandSender;

public class LoaderCommand extends SimpleCommand {

    public LoaderCommand() {
        super(true, "jrcloader");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("op"))
        {
            sender.sendMessage(textHead + "You don't have a permission.");
            return;
        }
        if(args.length == 0)
        {
            return;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            LoaderManager manager = LoaderManager.getInstance();
            manager.save();
            manager.load();
            sender.sendMessage(textHead + "Reload Confirmed");
            return;
        }
        sender.sendMessage(textHead + "/jrcloader reload");
        return;
    }
}
