package com.ut.rpg.core.commands.money.control;

import com.ut.rpg.core.commands.SimpleCommand;
import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.hooks.databases.MoneyDatabase;
import com.ut.rpg.core.managers.ConfigManager;
import com.ut.rpg.core.managers.PlayerManager;
import com.ut.rpg.core.objects.Account;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyControlCommand extends SimpleCommand {

    public MoneyControlCommand()
    {
        super(true, "돈관리");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.isOp())
        {
            if(args.length == 0)
            {
                sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 설정 [닉네임] [돈]");
                sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 추가 [닉네임] [돈]");
                sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 차감 [닉네임] [돈]");
                sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 초기화 [닉네임]");
                return;
            }
            if(args[0].equalsIgnoreCase("설정"))
            {
                if(args.length != 3)
                {
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 설정 [닉네임] [돈]");
                    return;
                }
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                try {
                    if(!MoneyDatabase.getInstance().isExists(offlinePlayer.getUniqueId()))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c데이터베이스에 존재하지 않는 플레이어입니다.");
                        return;
                    }
                    if(!StringUtil.isInteger(args[2]))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c돈은 정수로 입력해주세요.");
                        return;
                    }
                    MoneyDatabase.getInstance().upgradeAccountMoneyData(offlinePlayer.getUniqueId(), Long.parseLong(args[2]));
                    if(offlinePlayer.isOnline())
                        PlayerManager.getInstance().getPlayerAccount().get(offlinePlayer.getUniqueId()).setMoney(Long.parseLong(args[2]));
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "§a설정 완료");
                    if(sender instanceof Player)
                    {
                        Player p = (Player) sender;
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 1.5f,1.5f);
                    }
                    return;
                } catch (DatabaseHookException e) {
                    e.printStackTrace();
                }
                return;
            }
            if(args[0].equalsIgnoreCase("추가"))
            {
                if(args.length != 3)
                {
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 추가 [닉네임] [돈]");
                    return;
                }
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                try {
                    if(!MoneyDatabase.getInstance().isExists(offlinePlayer.getUniqueId()))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c데이터베이스에 존재하지 않는 플레이어입니다.");
                        return;
                    }
                    if(!StringUtil.isInteger(args[2]))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c돈은 정수로 입력해주세요.");
                        return;
                    }
                    Account account = MoneyDatabase.getInstance().getOriginalData(offlinePlayer.getUniqueId());
                    MoneyDatabase.getInstance().upgradeAccountMoneyData(offlinePlayer.getUniqueId(), account.getMoney() + Long.parseLong(args[2]));
                    if(offlinePlayer.isOnline())
                        PlayerManager.getInstance().getPlayerAccount().get(offlinePlayer.getUniqueId()).setMoney(account.getMoney() + Long.parseLong(args[2]));
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "§a설정 완료");
                    if(sender instanceof Player)
                    {
                        Player p = (Player) sender;
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 1.5f,1.5f);
                    }
                    return;
                } catch (DatabaseHookException e) {
                    e.printStackTrace();
                }
                return;
            }
            if(args[0].equalsIgnoreCase("차감"))
            {
                if(args.length != 3)
                {
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 차감 [닉네임] [돈]");
                    return;
                }
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                try {
                    if(!MoneyDatabase.getInstance().isExists(offlinePlayer.getUniqueId()))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c데이터베이스에 존재하지 않는 플레이어입니다.");
                        return;
                    }
                    if(!StringUtil.isInteger(args[2]))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c돈은 정수로 입력해주세요.");
                        return;
                    }
                    Account account = MoneyDatabase.getInstance().getOriginalData(offlinePlayer.getUniqueId());
                    MoneyDatabase.getInstance().upgradeAccountMoneyData(offlinePlayer.getUniqueId(), account.getMoney() - Long.parseLong(args[2]));
                    if(offlinePlayer.isOnline())
                        PlayerManager.getInstance().getPlayerAccount().get(offlinePlayer.getUniqueId()).setMoney(account.getMoney() - Long.parseLong(args[2]));
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "§a설정 완료");
                    if(sender instanceof Player)
                    {
                        Player p = (Player) sender;
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 1.5f,1.5f);
                    }
                    return;
                } catch (DatabaseHookException e) {
                    e.printStackTrace();
                }
                return;
            }
            if(args[0].equalsIgnoreCase("초기화"))
            {
                if(args.length != 2)
                {
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "/돈관리 초기화 [닉네임]");
                    return;
                }
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                try {
                    if(!MoneyDatabase.getInstance().isExists(offlinePlayer.getUniqueId()))
                    {
                        sender.sendMessage(ConfigManager.getInstance().getText_head() + "§c데이터베이스에 존재하지 않는 플레이어입니다.");
                        return;
                    }
                    MoneyDatabase.getInstance().upgradeAccountMoneyData(offlinePlayer.getUniqueId(), 0);
                    if(offlinePlayer.isOnline())
                        PlayerManager.getInstance().getPlayerAccount().get(offlinePlayer.getUniqueId()).setMoney(0);
                    sender.sendMessage(ConfigManager.getInstance().getText_head() + "§a설정 완료");
                    if(sender instanceof Player)
                    {
                        Player p = (Player) sender;
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 1.5f,1.5f);
                    }
                    return;
                } catch (DatabaseHookException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
