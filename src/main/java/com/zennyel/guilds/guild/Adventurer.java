package com.zennyel.guilds.guild;

public class Adventurer {

    private Rank rank;
    private double experience;

    private int fame;

    public Adventurer(Rank rank, double experience, int fame) {
        this.rank = rank;
        this.experience = experience;
        this.fame = fame;
    }

    public int getFame() {
        return fame;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }
}
