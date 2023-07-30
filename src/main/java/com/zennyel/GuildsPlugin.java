package com.zennyel;

import com.zennyel.guilds.GuildPlaceHolderExpansion;
import com.zennyel.guilds.command.GuildCommand;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.listener.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildsPlugin extends JavaPlugin {

    private long startTime;
    @Override
    public void onEnable(){
        init();
    }
    @Override
    public void onDisable(){

    }

    //initialize main modules.
    public void init(){
        startTime = System.currentTimeMillis();
        saveDefaultConfig();
        getLogger().info("Start loading...");

        loadDAO();
        registerListener();
        registerCommand();
        registerPlaceholder();

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        getLogger().info("Loading completed, took " + loadTime + "ms!");
    }

    public void shutdown(){
    }

    public void loadDAO(){
        new AdventurerDAO(this).createTable();
    }

    public void registerListener(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
    }

    public void registerCommand(){
        getCommand("guilds").setExecutor(new GuildCommand(this));
    }

    public void registerPlaceholder(){
        new GuildPlaceHolderExpansion().register();
    }





}
