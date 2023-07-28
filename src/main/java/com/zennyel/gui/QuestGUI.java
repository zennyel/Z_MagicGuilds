package com.zennyel.gui;

import com.zennyel.GuildsPlugin;
import com.zennyel.config.MenuConfiguration;
import com.zennyel.loader.ConfigLoader;
import com.zennyel.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestGUI extends AbstractGUI{
    private ConfigLoader configLoader;
    private MenuConfiguration menuConfiguration;

    public QuestGUI(GuildsPlugin instance) {
        super(instance);
        this.menuConfiguration = new MenuConfiguration(instance);
        this.configLoader = new ConfigLoader(menuConfiguration.getConfiguration());
        this.setInventory(Bukkit.createInventory(null, 6*9, configLoader.getString("Quest_ranks.menu_title")));
    }

    public void setItems() {
        setGlassesInBorders(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
        getInventory().setItem(5, playerHeadDisplay());
        Item(Material.FEATHER, "§6Next Page", "&7Click for the next page!", 43);
    }

    private ItemStack playerHeadDisplay(){
        String headName = ChatColor.GOLD + getPlayer().getName() + " missions";
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "My Rank: ");
        lore.add(ChatColor.GRAY + "My Fame: ");
        lore.add(ChatColor.GRAY + "Completed missions: ");
        lore.add(ChatColor.GRAY + "Available missions: ");
        return ItemStackUtils.createSkullItemStack(getPlayer().getName(), headName, lore);
    }

    public void setGlassesInBorders(Inventory inventory, Material glassType) {
        int rows = 6;
        int columns = 9;

        ItemStack glass = new ItemStack(glassType);

        for (int row = 0; row < rows; row++) {
            int leftSlot = row * columns;
            inventory.setItem(leftSlot, glass);
        }

        for (int row = 0; row < rows - 1; row++) {
            int rightSlot = (row * columns) + (columns - 1);
            inventory.setItem(rightSlot, glass);
        }
    }





}
