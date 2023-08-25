package com.zennyel.guilds.gui;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.config.MenuConfiguration;
import com.zennyel.guilds.loader.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class QuestRankGUI extends AbstractGUI{


    private MenuConfiguration menuConfiguration;
    private ConfigLoader configLoader;

    public QuestRankGUI(GuildsPlugin instance) {
        super(instance);
        this.menuConfiguration = new MenuConfiguration(instance);
        this.configLoader = new ConfigLoader(menuConfiguration.getConfiguration());
        this.setInventory(Bukkit.createInventory(null, 5*9, configLoader.getString("Quest_ranks.menu_title").replace("&", "§")));
    }
    public void setItems(){
        for(int i = 0; i < getInventory().getSize(); i++){
            Item(Material.STAINED_GLASS_PANE, "", "", i, DyeColor.GRAY.getData());
        }
        getInventory().setItem(10, configLoader.getItemStack("Quest_ranks.rank_f"));
        getInventory().setItem(12, configLoader.getItemStack("Quest_ranks.rank_e"));
        getInventory().setItem(14, configLoader.getItemStack("Quest_ranks.rank_d"));
        getInventory().setItem(16, configLoader.getItemStack("Quest_ranks.rank_c"));
        getInventory().setItem(19 + 9, configLoader.getItemStack("Quest_ranks.rank_b"));
        getInventory().setItem(21 + 9, configLoader.getItemStack("Quest_ranks.rank_a"));
        getInventory().setItem(23+ 9, configLoader.getItemStack("Quest_ranks.rank_s"));
    }

}
