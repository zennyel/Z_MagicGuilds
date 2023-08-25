package com.zennyel.guilds.listener.player;

import com.zennyel.GuildsPlugin;
import com.zennyel.events.stats.PlayerLevelChangeEvent;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.guild.quest.QuestType;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLevelUpListener implements Listener {

    private GuildsPlugin guildsPlugin;
    private QuestManager questManager;
    private AdventurerManager adventurerManager;
    private PartyManager partyManager;

    public PlayerLevelUpListener(GuildsPlugin guildsPlugin) {
        this.partyManager = guildsPlugin.getPartyCache();
        this.guildsPlugin = guildsPlugin;
        this.questManager = guildsPlugin.getQuestCache();
        this.adventurerManager = guildsPlugin.getCache();
    }

    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent event){
        Player player = event.getPlayer();
        if(questManager.getQuest(player) == null){
            return;
        }

        Quest quest = questManager.getQuest(player);

        Adventurer adventurer = adventurerManager.getAdventurer(player);

        if(!quest.getType().equals(QuestType.leveling)){
            return;
        }

        if(event.getNewLevel() > event.getOldLevel()){

            questManager.updateQuest(player, quest);
            quest.getObjective().setCount(quest.getObjective().getCount() - 1);
        }

        if(quest.getObjective().getCount() == 0){
            if(partyManager.getPartyOfPlayer(player) != null){
                partyManager.completePartyMission(player);
                return;
            }
            questManager.finalizeQuest(player, adventurer);
        }


    }

}
