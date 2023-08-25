package com.zennyel.guilds.manager;

import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AdventurerManager {

    private final AdventurerDAO adventurerDAO;
    private final HashMap<Player, Adventurer> adventurerHashMap;
    private final HashMap<Player, Boolean> isRegistered;


    public AdventurerManager(AdventurerDAO adventurerDAO) {
        this.adventurerDAO = adventurerDAO;
        this.adventurerHashMap = new HashMap<>();
        this.isRegistered = new HashMap<>();
    }

    public void setIsRegistered(Player player, Boolean b){
        isRegistered.put(player, b);
    }
    public Boolean isRegistered(Player player){
        if(!isRegistered.containsKey(player)){
            return false;
        }
        return isRegistered.get(player);
    }

    public void load() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            load(player);
        }
    }

    public void save() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            save(player);
        }
    }

    public void load(Player player) {
        Adventurer loadedAdventurer = adventurerDAO.get(player);
        Adventurer adventurer = (loadedAdventurer != null)
                ? loadedAdventurer
                : new Adventurer(Rank.F, 0, 0);
        setAdventurer(player, adventurer);
    }

    public void save(Player player) {
        Adventurer adventurer = getAdventurer(player);
        if (adventurer == null) {
            adventurer = new Adventurer(Rank.F, 0, 0);
            setAdventurer(player, adventurer);
        }
        adventurerDAO.insert(player, adventurer);
    }

    public void setAdventurer(Player player, Adventurer adventurer) {
        adventurerHashMap.put(player, adventurer);
    }

    public Adventurer getAdventurer(Player player) {
        return adventurerHashMap.get(player);
    }
}
