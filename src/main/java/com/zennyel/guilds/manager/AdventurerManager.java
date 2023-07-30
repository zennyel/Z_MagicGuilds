package com.zennyel.guilds.manager;

import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AdventurerManager {

    private AdventurerDAO adventurerDAO;
    private HashMap<Player, Adventurer> adventurerHashMap;

    public AdventurerManager(AdventurerDAO adventurerDAO) {
        this.adventurerDAO = adventurerDAO;
        this.adventurerHashMap = new HashMap<>();
    }

    public void load() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Adventurer adventurer = adventurerDAO.get(player);
            setAdventurer(player, adventurer);
        }
    }
    public void save(){
        for(Player player : Bukkit.getOnlinePlayers()){
            Adventurer adventurer = getAdventurer(player);
            adventurerDAO.insert(player, adventurer);
        }
    }

    public void setAdventurer(Player player, Adventurer adventurer){
        adventurerHashMap.put(player, adventurer);
    }
    public Adventurer getAdventurer(Player player){
        if(!adventurerHashMap.containsValue(player)){
            return null;
        }
        return adventurerHashMap.get(player);
    }


}
