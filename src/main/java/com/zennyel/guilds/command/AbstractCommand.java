package com.zennyel.guilds.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command,  String s,  String[] strings) {
        handleCommands(commandSender, command, s, strings);
        return false;
    }

    public boolean handleCommands(CommandSender sender, Command command, String s, String[] args){
        return false;
    }


}
