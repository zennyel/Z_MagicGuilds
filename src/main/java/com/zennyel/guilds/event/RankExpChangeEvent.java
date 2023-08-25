package com.zennyel.guilds.event;

import com.zennyel.guilds.command.AbstractCommand;
import com.zennyel.guilds.guild.Adventurer;
import org.bukkit.entity.Player;

public class RankExpChangeEvent extends AbstractEvent {

    Player player;
    private Adventurer adventurer;

    private double oldExperience;
    private double newExperience;

    public RankExpChangeEvent(Player player, Adventurer adventurer, double oldExperience, double newExperience) {
        this.player = player;
        this.adventurer = adventurer;
        this.oldExperience = oldExperience;
        this.newExperience = newExperience;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public double getOldExperience() {
        return oldExperience;
    }

    public void setOldExperience(double oldExperience) {
        this.oldExperience = oldExperience;
    }

    public double getNewExperience() {
        return newExperience;
    }

    public void setNewExperience(double newExperience) {
        this.newExperience = newExperience;
    }
}
