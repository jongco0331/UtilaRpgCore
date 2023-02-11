package com.ut.rpg.core.commands.stat.control;

import com.ut.rpg.core.commands.SimpleCommand;
import org.bukkit.command.CommandSender;

public class StatControlCommand extends SimpleCommand {

    public StatControlCommand() {
        super(true, "스텟관리");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.isOp())
        {
            if(args.length == 0)
            {
                sender.sendMessage("/스텟관리 설정 [타입] [닉네임] [수치]");
                sender.sendMessage("/스텟관리 추가 [타입] [닉네임] [수치]");
                sender.sendMessage("/스텟관리 차감 [타입] [닉네임] [수치]");
                sender.sendMessage("/스텟관리 초기화 [닉네임]");
                return;
            }
        }
    }
}
