package com.zennyel.guilds.command;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.MessagesConfiguration;
import com.zennyel.guilds.gui.ProfileGUI;
import com.zennyel.guilds.gui.QuestGUI;
import com.zennyel.guilds.gui.QuestRankGUI;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.loader.ConfigLoader;
import com.zennyel.guilds.manager.AdventurerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand extends AbstractCommand {

    private final GuildsPlugin guildsPlugin;
    private final ConfigLoader configLoader;
    private final MessagesConfiguration messagesConfiguration;
    private final AdventurerManager adventurerManager;

    public GuildCommand(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.messagesConfiguration = new MessagesConfiguration(guildsPlugin);
        this.configLoader = new ConfigLoader(messagesConfiguration.getConfiguration());
        this.adventurerManager = guildsPlugin.getCache();
    }

    @Override
    public boolean handleCommands(CommandSender sender, Command command, String s, String[] args) {
        Player player = getPlayer(sender);
        if (player == null) {
            return false;
        }

        if (handleProfileCommand(player, args) || handleQuestsCommand(player, args) || handleRegisterCommand(player, args)) {
            return true;
        }

        return handleDefaultCommand(player);
    }

    private Player getPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use guild commands.");
            return null;
        }
        return (Player) sender;
    }

    private boolean handleProfileCommand(Player player, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("profile")) {
            if (!player.hasPermission("zmagic.use")) {
                player.sendMessage("§cYou don't have permission to use this command!");
                return false;
            }

            ProfileGUI profileGUI = new ProfileGUI(guildsPlugin);
            profileGUI.setPlayer(player);
            profileGUI.addItems();
            player.openInventory(profileGUI.getInventory());
            return true;
        }
        return false;
    }

    private boolean handleQuestsCommand(Player player, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("quests")) {
            if (!player.hasPermission("zmagic.use")) {
                player.sendMessage("§cYou don't have permission to use this command!");
                return false;
            }

            if(!adventurerManager.isRegistered(player)){
                player.sendMessage("§cYou are not registered!");
                player.sendMessage("§cUse: /guild register");
                return false;
            }

            QuestRankGUI questRankGUI = new QuestRankGUI(guildsPlugin);
            questRankGUI.setPlayer(player);
            questRankGUI.setItems();
            player.openInventory(questRankGUI.getInventory());
            return true;
        }
        return false
                ;
    }

    private boolean handleRegisterCommand(Player player, String[] args){
        if(args.length >= 1 && args[0].equalsIgnoreCase("register")) {
            Adventurer adventurer = adventurerManager.getAdventurer(player);
            player.sendMessage("");
            player.sendMessage("§aWelcome " + player.getName());
            player.sendMessage("§6§lSucessfully registered to guild system!");
            player.sendMessage("§eActual rank: " + adventurer.getRank());
            player.sendMessage("");
            adventurerManager.setIsRegistered(player, true);
            return true;
        }
        return false;
    }
    private boolean handleDefaultCommand(Player player) {
            if (!player.hasPermission("zmagic.use")) {
                player.sendMessage("§cYou don't have permission to use this command!");
                return false;
            }

        if(!adventurerManager.isRegistered(player)){
            player.sendMessage("§cYou are not registered!");
            player.sendMessage("§cUse: /guild register");
            return false;
        }

            if (adventurerManager.getAdventurer(player) != null) {
                QuestGUI questGUI = new QuestGUI(guildsPlugin);
                questGUI.setPlayer(player);
                questGUI.setRank(adventurerManager.getAdventurer(player).getRank());
                questGUI.setItems();
                player.openInventory(questGUI.getInventory());
            }
            return true;
    }
}
