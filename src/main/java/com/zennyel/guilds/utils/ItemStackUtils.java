package com.zennyel.guilds.utils;

import com.zennyel.guilds.guild.quest.Quest;
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

    public static Quest getQuest(ItemStack itemStack, List<Quest> quests) {
        for (Quest quest : quests) {
            ItemStack questItem = createQuestItem(quest);
            if (questItem.isSimilar(itemStack)) {
                return quest;
            }
        }
        return null;
    }
    public static ItemStack createQuestItem(Quest quest) {
        Material material = Material.PAPER;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§5" + quest.getName());
        List<String> lore = new ArrayList<>();
        lore.add("§7Reward: " + quest.getReward().getName());
        lore.add("§7Location: " + "X-§f" + quest.getObjective().getLocation().getX() + " §7Y-§f" + quest.getObjective().getLocation().getY() + " §7Z-§f" + quest.getObjective().getLocation().getZ());
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
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createSkullItemStack(String playerName, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(playerName);
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
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
        String displayName = ChatColor.translateAlternateColorCodes('&', configuration.getString(configPath + ".displayName"));itemMeta.setDisplayName(displayName);

            List<String> loreList = configuration.getStringList(configPath + ".lore");
            List<String> formattedLore = new ArrayList<>();

            for (String lore : loreList) {
                formattedLore.add(ChatColor.translateAlternateColorCodes('&', lore));
            }

            itemMeta.setLore(formattedLore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
