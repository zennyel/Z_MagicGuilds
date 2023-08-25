package com.zennyel.guilds.builder;

import com.zennyel.character.ClassType;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.guild.quest.Objective;
import com.zennyel.guilds.guild.quest.Quest;
import com.zennyel.guilds.guild.quest.QuestType;
import com.zennyel.guilds.guild.quest.Reward;
import com.zennyel.guilds.loader.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class QuestBuilder {
    private final ConfigLoader configLoader;
    private final String questId;

    private String name;
    private Rank rank;
    private QuestType type;
    private Objective objective;
    private Reward reward;
    private int cooldown;

    public QuestBuilder(ConfigLoader configLoader, String questId) {
        this.configLoader = configLoader;
        this.questId = questId;
    }

    public QuestBuilder load() {
        loadBasicInfo();
        loadObjective();
        loadReward();
        return this;
    }

    private void loadBasicInfo() {
        name = configLoader.getString("Quests." + questId + ".name");
        rank = Rank.valueOf(configLoader.getString("Quests." + questId + ".rank"));
        type = QuestType.valueOf(configLoader.getString("Quests." + questId + ".type"));
        cooldown =  configLoader.getInt("Quests." + questId + ".cooldown");
    }

    private void loadObjective() {
        World world = Bukkit.getWorld(configLoader.getString("Quests." + questId + ".coordinates.world"));
        double x = configLoader.getDouble("Quests." + questId + ".coordinates.x");
        double y = configLoader.getDouble("Quests." + questId + ".coordinates.y");
        double z = configLoader.getDouble("Quests." + questId + ".coordinates.z");
        int count = configLoader.getInt("Quests." + questId + ".count");
        Location location = new Location(world, x, y, z);

        switch (type) {
            case boss:
                String bossName = configLoader.getString("Quests." + questId + ".boss_name");
                objective = new Objective(bossName, count, location);
                break;
            case leveling:
                objective = new Objective(count, location);
                break;
            case catching:
                String itemName = configLoader.getString("Quests." + questId + ".item_name");
                String itemId = configLoader.getString("Quests." + questId + ".item_id");
                objective = new Objective(itemName,itemId,count, location);
                break;
            case building:
            case mining:
                String blockMaterial = configLoader.getString("Quests." + questId + ".block_material");
                objective = new Objective(Material.getMaterial(blockMaterial), count, location);
                break;
            case kill:
                String mobName = configLoader.getString("Quests." + questId + ".mob_name");
                objective = new Objective(mobName, count, location);
                break;
            case player_kill:
                ClassType classType = ClassType.valueOf(configLoader.getString("Quests." + questId + ".player_class"));
                objective = new Objective(count, classType, location);
                break;
            case traveling:
                objective = new Objective(location);
        }
    }

    private void loadReward() {
        String rewardString = configLoader.getString("Quests." + questId + ".reward");
        String rewardCommand = configLoader.getString("Quests." + questId + ".reward_command");

        int experience = configLoader.getInt("Quests." + questId + ".reward_exp");
        int fame = configLoader.getInt("Quests." + questId + ".reward_fame");

        reward = new Reward(rewardString, rewardCommand, experience, fame);
    }

    public Quest build() {
        if (name == null || rank == null || type == null || objective == null) {
            return null;
        }

        return new Quest(name, rank, type, objective, reward, cooldown);
    }
}
