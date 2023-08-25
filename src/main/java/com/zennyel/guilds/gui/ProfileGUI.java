package com.zennyel.guilds.gui;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.MenuConfiguration;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.loader.ConfigLoader;
import com.zennyel.guilds.loader.QuestsLoader;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import com.zennyel.guilds.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileGUI extends AbstractGUI{

    private ConfigLoader configLoader;
    private QuestsLoader questsLoader;
    private MenuConfiguration menuConfiguration;
    private AdventurerManager adventurerManager;
    private QuestManager questManager;
    private PartyManager partyManager;
    public ProfileGUI(GuildsPlugin instance) {
        super(instance);
        this.menuConfiguration = new MenuConfiguration(instance);
        this.configLoader = new ConfigLoader(menuConfiguration.getConfiguration());
        this.adventurerManager = getInstance().getCache();
        this.questManager = instance.getQuestCache();
        this.partyManager = getInstance().getPartyCache();
        this.setInventory(Bukkit.createInventory(null, 9*3, configLoader.getString("Quest_profile.menu_title").replace("&", "§")));
    }

    public void addItems(){
        for(int i = 0; i < getInventory().getSize(); i++){
            Item(Material.STAINED_GLASS_PANE, "", "", i, DyeColor.GRAY.getData());
        }
        getInventory().setItem(10, playerHeadDisplay());
        getInventory().setItem(16, partyDisplay());
        questDisplay();
    }

    public void questDisplay(){
        List<String> lore = new ArrayList<>();
        if(questManager.getQuest(getPlayer()) == null){
            lore.add("§7Currently no active quests");
        }else {
            Quest quest = questManager.getQuest(getPlayer());
            switch (quest.getType()){
                case boss:
                    lore.add("§7Boss: §e" + quest.getObjective().getMobName() + " §7(§f" + quest.getObjective().getCount() + "§7)");
                    break;
                case kill:
                    lore.add("§7Kill Count: " + quest.getObjective().getCount());
                    lore.add("§7Mob: §e" + quest.getObjective().getMobName());
                    break;
                case mining:
                case building:
                    lore.add("§7Blocks Remaining: " + quest.getObjective().getCount());
                    lore.add("§7Block Type: §e" + quest.getObjective().getBlockType().toString());
                    break;
                case catching:
                    lore.add("§7Items Remaining: " + quest.getObjective().getCount());
                    lore.add("§7Item Type: " + Material.getMaterial(quest.getObjective().getItemId()).toString());
                    lore.add("§7Item Name: §e" + quest.getObjective().getItemName());
                    break;
                case leveling:
                    lore.add("§7Levels Remaining: §e" + quest.getObjective().getCount());
                case player_kill:
                    lore.add("§7Remaining targets: " + quest.getObjective().getCount());
                    lore.add("§7Target Class: " + quest.getObjective().getClassType());
                    break;
            }
        }
        ItemStack is = Item(Material.BOOK_AND_QUILL, "§6Active quest", lore,13 , (short) 0);
    }

    public ItemStack partyDisplay(){
        ItemStack bannerItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta bannerMeta = bannerItem.getItemMeta();
        bannerMeta.setDisplayName("§6Party Members");
        List<String> lore = new ArrayList<>();
        if(partyManager.getPartyOfPlayer(getPlayer()) == null){
            lore.add("§7Actually not in a party!");
        }else{
            for(Player player : partyManager.getPartyOfPlayer(getPlayer()).getMembers()){
                if(!Objects.equals(player.getName(), getPlayer().getName())){
                    lore.add("§7- " + player.getName());
                }
            }
        }
        bannerMeta.setLore(lore);
        bannerItem.setItemMeta(bannerMeta);
        return bannerItem;
    }

    private ItemStack playerHeadDisplay(){
        String headName = ChatColor.GOLD + getPlayer().getName() + " missions";
        Adventurer adventurer = adventurerManager.getAdventurer(getPlayer());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "My Rank: " + adventurer.getRank());
        lore.add(ChatColor.GRAY + "My Fame: " + adventurer.getFame());
        lore.add(ChatColor.GRAY + "My Experience: " + adventurer.getExperience());
        return ItemStackUtils.createSkullItemStack(getPlayer().getName(), headName, lore);
    }



}
