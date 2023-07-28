package com.zennyel.command;

import com.zennyel.GuildsPlugin;
import com.zennyel.config.MessagesConfiguration;
import com.zennyel.gui.QuestGUI;
import com.zennyel.loader.ConfigLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GuildCommand extends AbstractCommand {

    private GuildsPlugin guildsPlugin;
    private ConfigLoader configLoader;
    private MessagesConfiguration messagesConfiguration;

    public GuildCommand(GuildsPlugin guildsPlugin){
        this.guildsPlugin = guildsPlugin;
        this.messagesConfiguration = new MessagesConfiguration(guildsPlugin);
        this.configLoader = new ConfigLoader(messagesConfiguration.getConfiguration());
    }

    @Override
    public void handleCommands(CommandSender sender, Command command, String s, String[] args) {
        handleQuestsCommand(sender, args);
    }

    public void handleQuestsCommand(CommandSender sender, String[] args){

        if(!(sender instanceof Player)){
            return;
        }

        Player player = ((Player) sender).getPlayer();

        if(!player.hasPermission("")){
            player.sendMessage(configLoader.getString(""));
            return;
        }

        if(args.length != 1){
            player.sendMessage(configLoader.getString(""));
            return;
        }

        if(!args[0].equalsIgnoreCase("quests")){
            return;
        }


        QuestGUI questGUI = new QuestGUI(guildsPlugin);
        questGUI.setItems();
        questGUI.setPlayer(player);

        player.openInventory(questGUI.getInventory());
    }


}
