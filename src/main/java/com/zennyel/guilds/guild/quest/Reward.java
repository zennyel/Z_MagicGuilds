package com.zennyel.guilds.guild.quest;

import org.bukkit.command.Command;

public class Reward {

    String name;
    String command;

    private double experience;

    private int fama;

    public Reward(String name, String command, double experience, int fama) {
        this.name = name;
        this.command = command;
        this.experience = experience;
        this.fama = fama;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getFama() {
        return fama;
    }

    public void setFama(int fama) {
        this.fama = fama;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
