package com.ut.rpg.core.commands.stat;

import com.ut.rpg.core.commands.SimpleCommand;
import com.ut.rpg.core.guis.stat.StatGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatCommand extends SimpleCommand {

    public StatCommand()
    {
        super(false, "스텟");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player)
        {
            new StatGui((Player) sender);
            return;
        }
    }
}
