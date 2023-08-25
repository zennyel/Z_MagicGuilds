package com.zennyel.guilds.manager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class QuestCooldownManager {

    private final Map<Player, Map<String, Long>> playerQuestCooldowns;
    private final Plugin plugin;

    public QuestCooldownManager(Plugin plugin) {
        this.playerQuestCooldowns = new HashMap<>();
        this.plugin = plugin;

        new CooldownUpdater().runTaskTimer(plugin, 20L, 20L); // Run every second (adjust as needed)
    }

    public void setQuestCooldown(Player player, String questName, long cooldownSeconds) {
        long cooldownMillis = cooldownSeconds * 1000; // Convertendo segundos para milissegundos
        long currentTime = System.currentTimeMillis();
        long cooldownEndTime = currentTime + cooldownMillis;

        playerQuestCooldowns.computeIfAbsent(player, k -> new HashMap<>()).put(questName, cooldownEndTime);
    }


    public long getQuestCooldownRemaining(Player player, String questName) {
        Map<String, Long> questCooldowns = playerQuestCooldowns.get(player);
        if (questCooldowns != null) {
            Long cooldownEndTime = questCooldowns.get(questName);
            if (cooldownEndTime != null) {
                long currentTime = System.currentTimeMillis();
                long value = cooldownEndTime - currentTime;
                return value/1000;
            }
        }
        return 0;
    }


    private class CooldownUpdater extends BukkitRunnable {
        @Override
        public void run() {
            for (Map<String, Long> questCooldowns : playerQuestCooldowns.values()) {
                long currentTime = System.currentTimeMillis();
                questCooldowns.entrySet().removeIf(entry -> entry.getValue() <= currentTime);
            }
        }
    }
}
