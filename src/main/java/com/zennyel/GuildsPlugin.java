package com.zennyel;

import com.zennyel.command.GuildCommand;
import com.zennyel.listener.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildsPlugin extends JavaPlugin {

    private long startTime;

    @Override
    public void onDisable() {
        init();
    }

    @Override
    public void onEnable() {
        shutdown();
    }

    //initialize main modules.
    public void init(){
        startTime = System.currentTimeMillis();
        saveDefaultConfig();
        getLogger().info("Start loading...");

        registerListener();
        registerCommand();

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        getLogger().info("Loading completed, took " + loadTime + "ms!");
    }

    public void shutdown(){

    }

    public void registerListener(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
    }

    public void registerCommand(){
        getCommand("guilds").setExecutor(new GuildCommand(this));
    }





}
