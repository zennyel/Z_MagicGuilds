package com.zennyel.guilds.listener;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.boss.api.event.BossDeathEvent;

public class BossDeathListener implements Listener {
    private GuildsPlugin guildsPlugin;
    private AdventurerManager adventurerManager;
    private QuestManager questManager;
    private PartyManager partyManager;


    public BossDeathListener(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
        this.questManager = guildsPlugin.getQuestCache();
        this.partyManager = guildsPlugin.getPartyCache();
    }

    @EventHandler
    public void onBossDeath(BossDeathEvent event) {
        Player player = event.getEntity().getKiller();

        if (questManager.getQuest(player) == null) {
            return;
        }

        Quest quest = questManager.getQuest(player);

        if (event.getBoss().getName().equalsIgnoreCase(quest.getObjective().getMobName())) {
            quest.getObjective().setCount(quest.getObjective().getCount() - 1);
        }

        questManager.updateQuest(player, quest);

        if (quest.getObjective().getCount() == 0) {
            if(partyManager.getPartyOfPlayer(player) != null){
                partyManager.completePartyMission(player);
                return;
            }
            questManager.finalizeQuest(player, adventurerManager.getAdventurer(player));
        }

    }

}
