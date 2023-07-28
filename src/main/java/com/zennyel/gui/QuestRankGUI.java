package com.zennyel.gui;

import com.zennyel.GuildsPlugin;
import com.zennyel.loader.ConfigLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class QuestRankGUI extends AbstractGUI{

    private ConfigLoader configLoader;

    public QuestRankGUI(GuildsPlugin instance) {
        super(instance);
        setItems();
    }
    public void setItems(){
        for(int i = 0; i < getInventory().getSize(); i++){
            Item(Material.GRAY_STAINED_GLASS_PANE, "", "", i);
        }
        getInventory().setItem(10, configLoader.getItemStack("Quest_ranks.rank_f"));
        getInventory().setItem(12, configLoader.getItemStack("Quest_ranks.rank_e"));
        getInventory().setItem(14, configLoader.getItemStack("Quest_ranks.rank_d"));
        getInventory().setItem(16, configLoader.getItemStack("Quest_ranks.rank_c"));
        getInventory().setItem(19, configLoader.getItemStack("Quest_ranks.rank_b"));
        getInventory().setItem(21, configLoader.getItemStack("Quest_ranks.rank_a"));
        getInventory().setItem(23, configLoader.getItemStack("Quest_ranks.rank_s"));
    }

}
