package com.ut.rpg.core.commands.item;

import com.ut.rpg.core.commands.SimpleCommand;
import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.hooks.databases.ItemDatabase;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.managers.item.ItemManager;
import com.ut.rpg.core.utils.GuiUtil;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DBItemCommand extends SimpleCommand {

    public DBItemCommand()
    {
        super(false, "dbitem");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("op"))
            sender.sendMessage("§cYou don't have a permission.");
        Player p = (Player) sender;
        if(args.length == 0)
        {
            p.sendMessage(textHead + "/dbitem save [id] - 데이터베이스에서 손에 든 아이템을 [id]로 저장합니다.");
            p.sendMessage(textHead + "/dbitem remove [id] - 데이터베이스에서 [id]를 제거합니다.");
            p.sendMessage(textHead + "/dbitem reload - 데이터베이스 데이터를 리로드합니다. (save 또는 remove 시 각각 번지서버에서 입력 필요.)");
            p.sendMessage(textHead + "/dbitem get [id] - 데이터베이스에서 [id] 아이템을 인벤토리에 불러옵니다.");
            p.sendMessage(textHead + "/dbitem gui [integer] - 데이터베이스에서 [integer] id가 포함된 아이템을 gui에 불러옵니다.");
            p.sendMessage(textHead + "/dbitem guiAll - 데이터베이스에서 모든 아이템을 gui에 불러옵니다.");
            return;
        }
        ItemDatabase databaseManager = ItemDatabase.getInstance();
        if(args[0].equalsIgnoreCase("save"))
        {
            String id = args[1];
            if(StringUtil.isInteger(id))
            {
                if(p.getItemInHand() == null)
                {
                    p.sendMessage(textHead + "§c아이템을 들고 해주세요.");
                    return;
                }
                try {
                    if(databaseManager.isExist("uuid", Integer.parseInt(id)))
                    {
                        p.sendMessage(textHead + "§c이미 존재하는 id입니다. : " + id);
                        return;
                    }
                    databaseManager.put(id, p.getItemInHand());
                    p.sendMessage(textHead + "§a추가 완료.");
                } catch(DatabaseHookException e) {
                    e.printStackTrace();
                }
                return;
            }
            p.sendMessage(textHead + "§c아이디는 정수로 입력해주세요.");
        }
        if(args[0].equalsIgnoreCase("remove"))
        {
            String id = args[1];
            if(!StringUtil.isInteger(id))
            {
                p.sendMessage(textHead + "§cid는 정수로 입력해주세요");
                return;
            }
            try {
                if(!databaseManager.isExist("uuid", Integer.parseInt(id)))
                {
                    p.sendMessage(textHead + "§c존재하지 않는 id입니다. : " + id);
                    return;
                }
                databaseManager.remove("uuid", Integer.parseInt(id));
                p.sendMessage(textHead + "§a삭제 완료.");
                return;
            } catch(DatabaseHookException e)
            {
                e.printStackTrace();
            }
        }
        if(args[0].equalsIgnoreCase("get"))
        {
            String id = args[1];
            if(!StringUtil.isInteger(id))
            {
                p.sendMessage(textHead + "§cid는 정수로 입력해주세요");
                return;
            }
            try {
                if(!databaseManager.isExist("uuid", Integer.parseInt(id)))
                {
                    p.sendMessage(textHead + "§c존재하지 않는 id입니다. : " + id);
                    return;
                }
                ItemStack stack = (ItemStack) databaseManager.get("uuid", Integer.parseInt(id));
                p.getInventory().addItem(stack);
                return;
            } catch(DatabaseHookException e)
            {
                e.printStackTrace();
            }
        }
        if(args[0].equalsIgnoreCase("gui")) {
            String id = args[1];
            if (!StringUtil.isInteger(id)) {
                p.sendMessage(textHead + "§cid는 정수로 입력해주세요");
                return;
            }
            Inventory inv = Bukkit.createInventory(null, 9 * 6, "[ ITEM DATABASE ]");
            int i = 0;
            for(Map.Entry<Integer, ItemStack> stackList : ItemManager.getInstance().getDbItemHashMap().entrySet())
            {
                if(("" + stackList.getKey()).contains(id + ""))
                {
                    inv.setItem(i, stackList.getValue());
                    i++;
                }
            }
            p.openInventory(inv);
        }
        if(args[0].equalsIgnoreCase("guiAll")) {
            Inventory inv = Bukkit.createInventory(null, 9 * 6, "[ ITEM DATABASE ] 1");
            int i = 0;
            for(Map.Entry<Integer, ItemStack> stackList : ItemManager.getInstance().getDbItemHashMap().entrySet())
            {
                if(i == 45)
                    return;
                inv.setItem(i, stackList.getValue());
                i++;
            }
            inv.setItem(47, GuiUtil.makeStack(Material.NAME_TAG, "§a이전페이지"));
            inv.setItem(51, GuiUtil.makeStack(Material.NAME_TAG, "§a다음페이지"));
            p.openInventory(inv);
        }
        return;
    }
}
