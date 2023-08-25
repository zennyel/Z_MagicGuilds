package com.zennyel.guilds.gui;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.MenuConfiguration;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.loader.ConfigLoader;
import com.zennyel.guilds.loader.QuestsLoader;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class QuestGUI extends AbstractGUI{
    private ConfigLoader configLoader;
    private QuestsLoader questsLoader;
    private MenuConfiguration menuConfiguration;
    private AdventurerManager adventurerManager;
    private Rank rank;

    public QuestGUI(GuildsPlugin instance) {
        super(instance);
        this.menuConfiguration = new MenuConfiguration(instance);
        this.configLoader = new ConfigLoader(menuConfiguration.getConfiguration());
        this.adventurerManager = getInstance().getCache();
        this.setInventory(Bukkit.createInventory(null, 6*9, configLoader.getString("Quest_menu.menu_title").replace("&", "§")));
        this.questsLoader = new QuestsLoader(instance);
    }

    public void setItems() {
        setGlassesInBorders(getInventory(), Material.STAINED_GLASS_PANE);
        getInventory().setItem(4, playerHeadDisplay());
        loadQuests();
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

    public void setGlassesInBorders(Inventory inventory, Material glassType) {
        int rows = 6;
        int columns = 9;

        ItemStack glass = new ItemStack(glassType, 1, DyeColor.GRAY.getData());

        int[] slots = new int[] {0, 1, 2,3,4,5,6,7,8, 45, 46,47, 48, 49, 50, 51, 52, 53};

        for(int i : slots){
            inventory.setItem(i, glass);
        }

        for (int row = 0; row < rows; row++) {
            int leftSlot = row * columns;
            inventory.setItem(leftSlot, glass);
        }

        for (int row = 0; row < rows - 1; row++) {
            int rightSlot = (row * columns) + (columns - 1);
            inventory.setItem(rightSlot, glass);
        }
    }


    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void loadQuests() {
        if(questsLoader.loadQuests(getRank()) == null){
            return;
        }

        Set<Quest> quests = questsLoader.loadQuests(getRank());

        int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33};

        List<Quest> shuffledQuests = new ArrayList<>(quests);
        Collections.shuffle(shuffledQuests);

        for (int i = 0; i < Math.min(shuffledQuests.size(), slots.length); i++) {
            getInventory().setItem(slots[i], ItemStackUtils.createQuestItem(shuffledQuests.get(i)));
        }
    }







}
