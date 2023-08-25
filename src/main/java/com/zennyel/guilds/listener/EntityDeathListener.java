package com.zennyel.guilds.listener;

import com.zennyel.GuildsPlugin;
import com.zennyel.Z_FantasyCore;
import com.zennyel.character.Character;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.guild.quest.QuestType;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import com.zennyel.managers.character.CharacterManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private GuildsPlugin guildsPlugin;
    private AdventurerManager adventurerManager;
    private CharacterManager characterManager;
    private QuestManager questManager;
    private PartyManager partyManager;

    public EntityDeathListener(GuildsPlugin guildsPlugin) {
        this.partyManager = guildsPlugin.getPartyCache();
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
        this.questManager = guildsPlugin.getQuestCache();
        this.characterManager = Z_FantasyCore.getPlugin(Z_FantasyCore.class).getPluginManager().getCharacterManager();
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        Player player = ((LivingEntity) entity).getKiller();

        if(questManager.getQuest(player) == null){
            return;
        }

        Quest quest = questManager.getQuest(player);

        if(quest.getType() != QuestType.kill){
            return;
        }

        String name = event.getEntity().getName();

        if(!quest.getObjective().getMobName().isEmpty()){
            if(!name.equalsIgnoreCase(quest.getObjective().getMobName())){
                quest.getObjective().setCount(quest.getObjective().getCount() - 1);
                return;
            }
        }

        questManager.updateQuest(player, quest);

        if(quest.getObjective().getCount() == 0) {
            if(partyManager.getPartyOfPlayer(player) != null){
                partyManager.completePartyMission(player);
                return;
            }
            questManager.finalizeQuest(player, adventurerManager.getAdventurer(player));
        }

    }

}
