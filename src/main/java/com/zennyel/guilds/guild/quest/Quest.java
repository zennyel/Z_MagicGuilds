package com.zennyel.guilds.guild.quest;

import com.zennyel.guilds.guild.Rank;
import org.bukkit.Bukkit;

public class Quest {


    private String name;
    private Rank rank;
    private QuestType type;
    private Objective objective;
    private Reward reward;
    private boolean isActive;
    private int cooldown;

    public Quest(String name, Rank rank, QuestType type, Objective objective, Reward reward, int cooldown) {
        this.name = name;
        this.rank = rank;
        this.type = type;
        this.objective = objective;
        this.reward = reward;
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Objective getObjective() {
        return objective;
    }



    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public QuestType getType() {
        return type;
    }

    public void setType(QuestType type) {
        this.type = type;
    }
}
