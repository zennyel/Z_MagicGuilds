package com.zennyel.manager;

import com.zennyel.guild.quest.Quest;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class QuestManager {

    private HashMap<Player, Quest> questHashMap;

    public QuestManager(){
        questHashMap = new HashMap<>();
    }

    public Quest getQuest(Player player){
        return questHashMap.get(player);
    }
    public void giveQuest(Player player, Quest quest){
        questHashMap.put(player, quest);
    }

    public void removeQuest(Player player){
        questHashMap.remove(player);
    }

}
