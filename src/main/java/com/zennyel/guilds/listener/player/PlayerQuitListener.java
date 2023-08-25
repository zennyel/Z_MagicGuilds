package com.zennyel.guilds.listener.player;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.manager.AdventurerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private GuildsPlugin guildsPlugin;

    private AdventurerManager adventurerManager;


    public PlayerQuitListener(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        handlePlayerSave(event);
    }

    public void handlePlayerSave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(adventurerManager.getAdventurer(player) == null){
            return;
        }
        adventurerManager.save(player);
    }



}
