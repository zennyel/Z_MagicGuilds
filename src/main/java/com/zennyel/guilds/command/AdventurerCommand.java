package com.zennyel.guilds.command;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.manager.AdventurerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdventurerCommand extends AbstractCommand {

    private final GuildsPlugin guildsPlugin;
    private final AdventurerManager adventurerManager;

    public AdventurerCommand(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
    }

    @Override
    public boolean handleCommands(CommandSender sender, Command command, String s, String[] args) {
        Player player = getPlayer(sender);
        if (player == null) {
            return false;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /adventurer <setfame|setrank|setexp>");
            return false;
        }

        if (!player.hasPermission("zmagic.admin")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return false;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "setfame":
                handleSetFameCommand(player, args);
                return true;
            case "setexp":
                handleSetExpCommand(player, args);
                return true;
            case "setrank":
                handleSetRankCommand(player, args);
                return true;
            default:
                return false;
        }
    }

    private Player getPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use adventurer commands.");
            return null;
        }
        return (Player) sender;
    }

    private void handleSetFameCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /adventurer setfame <amount>");
            return;
        }

        int fameAmount = Integer.parseInt(args[1]);
        Adventurer adventurer = adventurerManager.getAdventurer(player);
        if (adventurer != null) {
            adventurer.setFame(fameAmount);
            player.sendMessage(ChatColor.GREEN + "Set fame to " + ChatColor.YELLOW + fameAmount + ChatColor.GREEN + " for " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");
        } else {
            player.sendMessage(ChatColor.RED + "You are not an adventurer.");
        }
    }

    private void handleSetExpCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /adventurer setexp <amount>");
            return;
        }

        double expAmount = Double.parseDouble(args[1]);
        Adventurer adventurer = adventurerManager.getAdventurer(player);
        if (adventurer != null) {
            adventurer.setExperience(expAmount);
            player.sendMessage(ChatColor.GREEN + "Set experience to " + ChatColor.YELLOW + expAmount + ChatColor.GREEN + " for " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");
        } else {
            player.sendMessage(ChatColor.RED + "You are not an adventurer.");
        }
    }

    private void handleSetRankCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /adventurer setrank <rank>");
            return;
        }

        String rankName = args[1].toUpperCase();
        try {
            Rank rank = Rank.valueOf(rankName);
            Adventurer adventurer = adventurerManager.getAdventurer(player);
            if (adventurer != null) {
                adventurer.setRank(rank);
                player.sendMessage(ChatColor.GREEN + "Set rank to " + ChatColor.YELLOW + rank + ChatColor.GREEN + " for " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + ".");
            } else {
                player.sendMessage(ChatColor.RED + "You are not an adventurer.");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid rank.");
        }
    }
}



