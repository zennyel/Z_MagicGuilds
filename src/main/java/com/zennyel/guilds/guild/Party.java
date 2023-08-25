package com.zennyel.guilds.guild;

import org.bukkit.entity.Player;

import java.util.List;

public class Party {

    private List<Player> members;

    private Player owner;

    public Party(List<Player> members, Player owner) {
        this.members = members;
        this.owner = owner;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
