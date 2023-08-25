package com.zennyel.guilds.gui;

import com.zennyel.GuildsPlugin;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGUI {

    private GuildsPlugin instance;
    private Inventory inventory;
    private Player player;

    public AbstractGUI(GuildsPlugin instance) {
        this.instance = instance;
    }

    public ItemStack Item(Material material, String displayName, String description, int slotPosition, short data){
        ItemStack item = new ItemStack(material, 1, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.setItem(slotPosition, item);
        return null;
    }

    public ItemStack Item(Material material, String displayName,List<String> description, int slotPosition, short data){
        ItemStack item = new ItemStack(material, 1, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(description);
        item.setItemMeta(meta);
        MaterialData materialData = item.getData();
        item.setData(materialData);
        inventory.setItem(slotPosition, item);
        return null;
    }

    public GuildsPlugin getInstance() {
        return instance;
    }

    public void setInstance(GuildsPlugin instance) {
        this.instance = instance;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
