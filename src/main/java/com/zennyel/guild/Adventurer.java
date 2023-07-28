package com.zennyel.guild;

public class Adventurer {

    private Rank rank;
    private double experience;

    public Adventurer(Rank rank, double experience) {
        this.rank = rank;
        this.experience = experience;
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
