package com.ut.rpg.core.commands.item;

import com.ut.rpg.core.commands.SimpleCommand;
import com.ut.rpg.core.loaders.item.ItemDataLoader;
import com.ut.rpg.core.managers.item.ItemManager;
import com.ut.rpg.core.managers.LoaderManager;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand extends SimpleCommand {

    public ItemCommand()
    {
        super(false, "itemdata");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("op"))
        {
            sender.sendMessage("§cYou don't have a permission.");
            return;
        }

        Player p = (Player) sender;
        if(args.length == 0)
        {
            p.sendMessage(textHead + "/itemdata get [id] - 아이템데이터에서 [id] 아이템을 불러옵니다.");
            p.sendMessage(textHead + "/itemdata reload - 아이템데이터를 리로드합니다.");
            return;
        }

        ItemManager itemManager = ItemManager.getInstance();

        if(args[0].equalsIgnoreCase("get"))
        {
            if(args.length == 1)
            {
                p.sendMessage(textHead + "§cid를 입력해주세요.");
                return;
            }
            if(StringUtil.isInteger(args[1]))
            {
                int id = Integer.parseInt(args[1]);
                if(!itemManager.getIdItemHashMap().containsKey(id))
                {
                    p.sendMessage(textHead + "§c존재하지 않는 id입니다.");
                    return;
                }
                p.getInventory().addItem(itemManager.getIdItemHashMap().get(id));
                return;
            }
            p.sendMessage(textHead + "§cid는 정수로 입력해주세요.");
            return;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            ItemDataLoader itemDataLoader = (ItemDataLoader) LoaderManager.getInstance().getLoaders().get(2);
            itemDataLoader.save();
            itemDataLoader.load();
            p.sendMessage(textHead + "§aReload Confirm.");
            return;
        }
        p.sendMessage(textHead + "/itemdata get [id] - 아이템데이터에서 [id] 아이템을 불러옵니다.");
        p.sendMessage(textHead + "/itemdata reload - 아이템데이터를 리로드합니다.");
        return;
    }
}
