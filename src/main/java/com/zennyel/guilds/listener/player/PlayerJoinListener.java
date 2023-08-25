package com.zennyel.guilds.listener.player;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.manager.AdventurerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    private GuildsPlugin guildsPlugin;
    private AdventurerManager adventurerManager;


    public PlayerJoinListener(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        handlePlayerLoad(event);
    }

    public void handlePlayerLoad(PlayerJoinEvent event){
        Player player = event.getPlayer();
        adventurerManager.load(player);
    }


}
