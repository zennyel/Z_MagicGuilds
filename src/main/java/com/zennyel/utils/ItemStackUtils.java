package com.zennyel.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackUtils {

    public static ItemStack createItemStack(Material material, String displayName, String description) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createSkullItemStack(String playerName, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getPlayer(playerName));
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
        meta.setUnbreakable(true);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItemFromConfiguration(String configPath, FileConfiguration configuration) {
        if (!configuration.contains(configPath)) {
            return null;
        }

        String materialName = configuration.getString(configPath + ".material");
        Material material = Material.matchMaterial(materialName);

        if (material == null) {
            return null;
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (configuration.contains(configPath + ".displayName")) {
            String displayName = ChatColor.translateAlternateColorCodes('&', configuration.getString(configPath + ".displayName"));
            itemMeta.setDisplayName(displayName);
        }

        if (configuration.contains(configPath + ".lore")) {
            List<String> loreList = configuration.getStringList(configPath + ".lore");
            List<String> formattedLore = new ArrayList<>();

            for (String lore : loreList) {
                formattedLore.add(ChatColor.translateAlternateColorCodes('&', lore));
            }

            itemMeta.setLore(formattedLore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
