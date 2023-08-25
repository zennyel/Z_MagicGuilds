package com.zennyel;

import com.zennyel.guilds.GuildPlaceHolderExpansion;
import com.zennyel.guilds.command.AdventurerCommand;
import com.zennyel.guilds.command.GuildCommand;
import com.zennyel.guilds.command.PartyCommand;
import com.zennyel.guilds.database.AdventurerDAO;
import com.zennyel.guilds.listener.*;
import com.zennyel.guilds.listener.player.*;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestCooldownManager;
import com.zennyel.guilds.manager.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildsPlugin extends JavaPlugin {

    private long startTime;
    private AdventurerManager cache;
    private PartyManager partyCache;
    private QuestManager questCache;
    private QuestCooldownManager cooldownCache;
    @Override
    public void onEnable(){
        init();
    }
    @Override
    public void onDisable(){
        shutdown();
    }
    public void init(){
        startTime = System.currentTimeMillis();
        getLogger().info("Start loading...");
        saveDefaultConfig();

        loadDAO();
        loadCache();
        registerListener();
        registerCommand();
        registerPlaceholder();


        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        getLogger().info("Loading completed, took " + loadTime + "ms!");
    }

    public void loadCache(){
        AdventurerDAO adventurerDAO = new AdventurerDAO(this);
        this.cache = new AdventurerManager(adventurerDAO);
        this.partyCache = new PartyManager(this);
        this.questCache = new QuestManager();
        this.cooldownCache = new QuestCooldownManager(this);
        cache.load();
    }


    public void shutdown(){
        getCache().save();
    }

    public void loadDAO(){
        new AdventurerDAO(this).createTable();
    }

    public void registerListener(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerLevelUpListener(this), this);
        pluginManager.registerEvents(new PlayerPickupItemListener(this), this);
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new BossDeathListener(this), this);
        pluginManager.registerEvents(new EntityDeathListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
    }

    public QuestCooldownManager getCooldownCache() {
        return cooldownCache;
    }

    public PartyManager getPartyCache() {
        return partyCache;
    }

    public long getStartTime() {
        return startTime;
    }

    public QuestManager getQuestCache() {
        return questCache;
    }

    public AdventurerManager getCache() {
        return cache;
    }

    public void registerCommand(){
        getCommand("guild").setExecutor(new GuildCommand(this));
        getCommand("party").setExecutor(new PartyCommand(this));
        getCommand("adventurer").setExecutor(new AdventurerCommand(this));
    }

    public void registerPlaceholder(){
        new GuildPlaceHolderExpansion().register();
    }





}
