package com.zennyel.guilds.guild.quest;

import com.zennyel.character.ClassType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Objective {

    String itemName;
    String itemId;
    String mobName;
    private ClassType classType;
    private Material blockType;
    private int count;
    private Location location;


    public Objective(int count, Location location) {
        this.count = count;
        this.location = location;
    }

    public Objective(int count, ClassType classType, Location location){
        this.count = count;
        this.classType = classType;
        this.location = location;
    }
    public Objective(Material blockType, int count, Location locatio) {
        this.blockType = blockType;
        this.count = count;
        this.location = locatio;
    }

    public Objective(Location location) {
        this.location = location;
    }

    public Objective(String itemName, String itemId, int count,  Location location) {
        this.itemName = itemName;
        this.location = location;
        this.count = count;
        this.itemId = itemId;
    }

    public Objective(String mobName, int count, Location location) {
        this.mobName = mobName;
        this.count = count;
        this.location = location;
    }

    public ClassType getClassType() {
        return classType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMobName() {
        return mobName;
    }

    public void setMobName(String mobName) {
        this.mobName = mobName;
    }

    public Material getBlockType() {
        return blockType;
    }

    public void setBlockType(Material blockType) {
        this.blockType = blockType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
