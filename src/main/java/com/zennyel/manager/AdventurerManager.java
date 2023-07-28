package com.zennyel.manager;

import com.zennyel.database.AdventurerDAO;
import com.zennyel.guild.Adventurer;
import com.zennyel.guild.Rank;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
