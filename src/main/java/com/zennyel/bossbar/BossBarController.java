package com.zennyel.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BossBarController {
    private Plugin plugin;
    private BossBar bossBar;

    public BossBarController(Plugin plugin) {
        this.plugin = plugin;
        this.bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
    }



    public void createBossBar(String message, BarColor color, BarStyle style) {
        bossBar.setTitle(message);
        bossBar.setColor(color);
        bossBar.setStyle(style);
    }

    public void showBossBar(Player player) {
        bossBar.addPlayer(player);
    }

    public void hideBossBar(Player player) {
        bossBar.removePlayer(player);
    }

    public void updateBossBar(String message) {
        bossBar.setTitle(message);
    }

    public void updateBossBarProgress(double progress) {
        bossBar.setProgress(progress);
    }

    public void showBossBarForDuration(Player player, String message, BarColor color, BarStyle style, long duration) {
        createBossBar(message, color, style);
        showBossBar(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                hideBossBar(player);
                bossBar.removeAll();
            }
        }.runTaskLater(plugin, duration);
    }


    public void pointBossBarToLocation(Location targetLocation) {
        for (Player player : bossBar.getPlayers()) {
            Location playerLocation = player.getLocation();
            Vector direction = targetLocation.toVector().subtract(playerLocation.toVector()).normalize();
            float pitch = playerLocation.getPitch();

            double x = -direction.getX();
            double z = -direction.getZ();
            double theta = Math.atan2(z, x);
            theta = theta < 0 ? theta + 2 * Math.PI : theta;

            double progress = theta / (2 * Math.PI);
            bossBar.setProgress(progress);

            BarColor color;
            if (pitch < -45) {
                color = BarColor.RED;
            } else if (pitch > 45) {
                color = BarColor.BLUE;
            } else {
                color = BarColor.GREEN;
            }
            bossBar.setColor(color);
        }
    }


}
