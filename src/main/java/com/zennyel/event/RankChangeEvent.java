package com.zennyel.event;

import com.zennyel.guild.Adventurer;
import com.zennyel.guild.Rank;

public class RankChangeEvent extends AbstractEvent{

    private Adventurer adventurer;
    private Rank oldRank;
    private Rank newRank;

    public RankChangeEvent(Adventurer adventurer, Rank oldRank, Rank newRank) {
        this.adventurer = adventurer;
        this.oldRank = oldRank;
        this.newRank = newRank;
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
