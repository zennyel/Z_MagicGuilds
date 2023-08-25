package com.zennyel.guilds.manager;

import com.zennyel.events.stats.PlayerLevelChangeEvent;
import com.zennyel.guilds.event.FameChangeEvent;
import com.zennyel.guilds.event.RankExpChangeEvent;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.guild.quest.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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

    public void finalizeQuest(Player player, Adventurer adventurer){

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getQuest(player).getReward().getCommand().replace("<player>", player.getName()));
        Reward reward = getQuest(player).getReward();

        int oldFame = adventurer.getFame();
        double oldExperience = adventurer.getExperience();

        int fame = reward.getFama();
        double experience = reward.getExperience();

        adventurer.setFame(adventurer.getFame() + fame);
        adventurer.setExperience(adventurer.getExperience() + experience);

        Bukkit.getPluginManager().callEvent(new FameChangeEvent(player, adventurer, oldFame, oldFame + fame));
        Bukkit.getPluginManager().callEvent(new RankExpChangeEvent(player, adventurer, oldExperience, oldExperience + experience));

        getQuest(player).setActive(false);

        Quest quest = getQuest(player);

        player.sendMessage(" ");
        player.sendMessage("§6§lQuest completed!");
        player.sendMessage("§eDetails: " + quest.getName());
        player.sendMessage("§eReward: " + quest.getReward().getName());
        player.sendMessage("§eFame: §f" + quest.getReward().getFama());
        player.sendMessage("§eExperience: §f" + quest.getReward().getExperience());
        player.sendMessage(" ");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);

        removeQuest(player);

    }

    public void updateQuest(Player player, Quest quest){
        Quest actual = getQuest(player);
        if(actual.getObjective().getCount() != quest.getObjective().getCount()) {
            questHashMap.put(player, quest);
        }
    }
    public void giveQuest(Player player, Quest quest){
        player.sendMessage("§a§lQuest started!");
        player.sendMessage("§eQuest details: " + quest.getName());
        player.sendMessage("§eLocation: " + "§7X-§f" + quest.getObjective().getLocation().getX() + " §7Y-§f" + quest.getObjective().getLocation().getY() + " §7Z-§f" + quest.getObjective().getLocation().getZ());
        questHashMap.put(player, quest);
    }

    public void removeQuest(Player player){
        questHashMap.remove(player);
    }

}
