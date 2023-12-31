package com.zennyel.guilds.event;

import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import org.bukkit.entity.Player;

public class RankChangeEvent extends AbstractEvent{

    Player player;
    private Adventurer adventurer;
    private Rank oldRank;
    private Rank newRank;

    public RankChangeEvent(Player player, Adventurer adventurer, Rank oldRank, Rank newRank) {
        this.player = player;
        this.adventurer = adventurer;
        this.oldRank = oldRank;
        this.newRank = newRank;
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

    public Rank getOldRank() {
        return oldRank;
    }

    public void setOldRank(Rank oldRank) {
        this.oldRank = oldRank;
    }

    public Rank getNewRank() {
        return newRank;
    }

    public void setNewRank(Rank newRank) {
        this.newRank = newRank;
    }
}
