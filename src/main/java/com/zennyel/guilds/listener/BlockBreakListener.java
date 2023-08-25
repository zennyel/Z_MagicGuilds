package com.zennyel.guilds.listener;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.guild.quest.QuestType;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {


    private GuildsPlugin guildsPlugin;
    private QuestManager questManager;
    private AdventurerManager adventurerManager;
    private PartyManager partyManager;

    public BlockBreakListener(GuildsPlugin guildsPlugin) {
        this.partyManager = guildsPlugin.getPartyCache();
        this.guildsPlugin = guildsPlugin;
        this.questManager = guildsPlugin.getQuestCache();
        this.adventurerManager = guildsPlugin.getCache();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();


        if(questManager.getQuest(player) == null){
            return;
        }

        Quest quest = questManager.getQuest(player);

        if(quest.getType() != QuestType.mining){
            return;
        }

        if(quest.getObjective().getBlockType().equals(event.getBlock().getType())){
            quest.getObjective().setCount(quest.getObjective().getCount() - 1);
        }
        questManager.updateQuest(player, quest);

        if(quest.getObjective().getCount() == 0){
            if(partyManager.getPartyOfPlayer(player) != null){
                partyManager.completePartyMission(player);
                return;
            }
            questManager.finalizeQuest(player, adventurerManager.getAdventurer(player));
        }


    }


}
