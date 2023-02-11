package com.ut.rpg.core.commands.money;

import com.ut.rpg.core.Lang;
import com.ut.rpg.core.commands.SimpleCommand;
import com.ut.rpg.core.managers.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand extends SimpleCommand {

    public MoneyCommand()
    {
        super(false, "돈");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(Lang.PRINT_MONEY.replaceAll("\\{0\\}", p.getName()).replaceAll("\\{1\\}", PlayerManager.getInstance().getPlayerAccount()
                .get(p.getUniqueId()).getMoney() + ""));
    }
}
