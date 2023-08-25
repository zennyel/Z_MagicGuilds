package com.zennyel.guilds.event;

import com.zennyel.guilds.guild.Adventurer;
import org.bukkit.entity.Player;

public class FameChangeEvent extends AbstractEvent{

    Player player;
    private Adventurer adventurer;

    private int oldFame;
    private int newFame;

    public FameChangeEvent(Player player, Adventurer adventurer, int oldFame, int newFame) {
        this.player = player;
        this.adventurer = adventurer;
        this.oldFame = oldFame;
        this.newFame = newFame;
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

    public int getOldFame() {
        return oldFame;
    }

    public void setOldFame(int oldFame) {
        this.oldFame = oldFame;
    }

    public int getNewFame() {
        return newFame;
    }

    public void setNewFame(int newFame) {
        this.newFame = newFame;
    }
}
