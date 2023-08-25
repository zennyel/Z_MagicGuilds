package com.zennyel.guilds.listener.adventurer;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.RanksConfiguration;
import com.zennyel.guilds.event.RankChangeEvent;
import com.zennyel.guilds.event.RankExpChangeEvent;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.loader.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RankExpChangeListener implements Listener {
    private GuildsPlugin guildsPlugin;
    private ConfigLoader configLoader;

    public RankExpChangeListener(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.configLoader = new ConfigLoader(new RanksConfiguration(guildsPlugin).getConfiguration());
    }

    @EventHandler
    public void onExpChange(RankExpChangeEvent event){

        Player player =event.getPlayer();
        Adventurer adventurer = event.getAdventurer();

        if(event.getNewExperience() > event.getOldExperience()){
            Rank oldRank = adventurer.getRank();
            Rank newRank = getNextRank(oldRank);
            if(event.getOldExperience() > rankupExperience(oldRank)) {
                Bukkit.getPluginManager().callEvent(new RankChangeEvent(player, adventurer, oldRank, newRank));
                adventurer.setRank(newRank);
                player.sendMessage("§aRanked up, congratulations!");
            }
        }

    }

    public int rankupExperience(Rank rank){
        return configLoader.getInt("Rank." + rank.toString());
    }

    public Rank getNextRank(Rank rank){
        switch (rank.getValue()){
            case 1:
                return Rank.E;
            case 2:
                return Rank.D;
            case 3:
                return Rank.C;
            case 4:
                return Rank.B;
            case 5:
                return Rank.A;
            case 6:
            case 7:
                return Rank.S;
        }
        return null;
    }

}
