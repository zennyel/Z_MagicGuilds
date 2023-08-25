package com.zennyel.guilds.listener;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.MenuConfiguration;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.gui.QuestGUI;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.loader.ConfigLoader;
import com.zennyel.guilds.loader.QuestsLoader;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestCooldownManager;
import com.zennyel.guilds.manager.QuestManager;
import com.zennyel.guilds.utils.ItemStackUtils;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class InventoryClickListener implements Listener {

    private AdventurerManager adventurerManager;
    private QuestManager questManager;
    private GuildsPlugin guildsPlugin;
    private HashMap<Player, Rank> lastChoosedRank;
    private MenuConfiguration menuConfiguration;
    private ConfigLoader configLoader;
    private QuestCooldownManager questCooldownManager;
    private QuestsLoader questsLoader;

    private PartyManager partyManager;
    public InventoryClickListener(GuildsPlugin guildsPlugin) {
        this.partyManager = guildsPlugin.getPartyCache();
        this.guildsPlugin = guildsPlugin;
        this.adventurerManager = guildsPlugin.getCache();
        this.questManager =  guildsPlugin.getQuestCache();
        this.lastChoosedRank = new HashMap<>();
        this.menuConfiguration = new MenuConfiguration(guildsPlugin);
        this.configLoader = new ConfigLoader(menuConfiguration.getConfiguration());
        this.questsLoader = new QuestsLoader(guildsPlugin);
        this.questCooldownManager = guildsPlugin.getCooldownCache();
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handleQuestRankGUI(event);
        handleQuestGUI(event);
        handleProfileGUI(event);
    }

    public void handleQuestRankGUI(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();


        String title = configLoader.getString("Quest_ranks.menu_title");


        if (!event.getView().getTitle().equalsIgnoreCase(title)) {
            return;
        }

        event.setCancelled(true);


        if(adventurerManager.getAdventurer(player) == null){
            return;
        }


        Adventurer adventurer = adventurerManager.getAdventurer(player);

        Rank rank = null;

        QuestGUI questGUI = new QuestGUI(guildsPlugin);
        questGUI.setPlayer(player);
        questGUI.setItems();


        switch (event.getRawSlot()){
            case 10:
                if(adventurer.getRank().getValue() >= 1){
                    rank = Rank.F;
                }
                break;
            case 12:
                if(adventurer.getRank().getValue() >= 2){
                    rank = Rank.E;
                }
                break;
            case 14:
                if(adventurer.getRank().getValue() >= 3){
                    rank = Rank.D;
                }
                break;
            case 16:
                if(adventurer.getRank().getValue() >= 4){
                    rank = Rank.C;
                }
                break;

            case 28:
                if(adventurer.getRank().getValue() >= 5){
                    rank = Rank.B;
                }
                break;
            case 30:
                if(adventurer.getRank().getValue() >= 6){
                    rank = Rank.A;
                }
                break;
            case 32:
                if(adventurer.getRank().getValue() == 7){
                    rank = Rank.S;
                }
                break;
        } switch (event.getRawSlot()){
            case 10:
                if(adventurer.getRank().getValue() >= 1){
                    rank = Rank.F;
                }
                break;
            case 12:
                if(adventurer.getRank().getValue() >= 2){
                    rank = Rank.E;
                }
                break;
            case 14:
                if(adventurer.getRank().getValue() >= 3){
                    rank = Rank.D;
                }
                break;
            case 16:
                if(adventurer.getRank().getValue() >= 4){
                    rank = Rank.C;
                }
                break;

            case 28:
                if(adventurer.getRank().getValue() >= 5){
                    rank = Rank.B;
                }
                break;
            case 30:
                if(adventurer.getRank().getValue() >= 6){
                    rank = Rank.A;
                }
                break;
            case 32:
                if(adventurer.getRank().getValue() == 7){
                    rank = Rank.S;
                }
                break;
        }



        questGUI.setRank(rank);
        lastChoosedRank.put(player, rank);
        player.openInventory(questGUI.getInventory());


    }

    public void handleProfileGUI(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        String title = configLoader.getString("Quest_profile.menu_title");

        if (!event.getView().getTitle().equalsIgnoreCase(title)) {
            return;
        }

        event.setCancelled(true);
    }


    public void handleQuestGUI(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        String title = configLoader.getString("Quest_menu.menu_title");

        if (!event.getView().getTitle().equalsIgnoreCase(title)) {
            return;
        }

        event.setCancelled(true);


        if(adventurerManager.getAdventurer(player) == null){
            return;
        }

        if(ItemStackUtils.getQuest(event.getCurrentItem(), questsLoader.loadAllQuests()) == null){
            return;
        }


        if(questManager.getQuest(player) != null){
            player.sendMessage("§cYou are already with a active quest! ");
            player.closeInventory();
            return;
        }

        Quest quest = ItemStackUtils.getQuest(event.getCurrentItem(), questsLoader.loadAllQuests());

        if(questCooldownManager.getQuestCooldownRemaining(player, quest.getName()) != 0){
            player.sendMessage("§cQuest in cooldown, please wait more " + questCooldownManager.getQuestCooldownRemaining(player, quest.getName())+ " seconds");
            return;
        }

        if(partyManager.getPartyOfPlayer(player) != null){
            if(partyManager.getPartyOfPlayer(player).getOwner() != player){
                player.sendMessage("§cOnly the party owner can start quests!");
                return;
            }

            for(Player member : partyManager.getPartyOfPlayer(player).getMembers()){
                questManager.giveQuest(member, quest);
                questCooldownManager.setQuestCooldown(member, quest.getName(), quest.getCooldown());

                member.closeInventory();
                return;
            }

        }

        questManager.giveQuest(player, quest);
        questCooldownManager.setQuestCooldown(player, quest.getName(), quest.getCooldown());

        player.closeInventory();

    }

}