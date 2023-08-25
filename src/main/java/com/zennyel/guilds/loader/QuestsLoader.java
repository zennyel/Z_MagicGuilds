package com.zennyel.guilds.loader;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.builder.QuestBuilder;
import com.zennyel.guilds.config.QuestsConfiguration;
import com.zennyel.guilds.guild.Rank;
import com.zennyel.guilds.guild.quest.Quest;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestsLoader {


    private GuildsPlugin guildsPlugin;
    private QuestsConfiguration questsConfiguration;
    private ConfigLoader configLoader;

    public QuestsLoader(GuildsPlugin guildsPlugin) {
        this.guildsPlugin = guildsPlugin;
        this.questsConfiguration = new QuestsConfiguration(this.guildsPlugin);
        this.configLoader = new ConfigLoader(questsConfiguration.getConfiguration());
    }

    public ArrayList<Quest> loadAllQuests(){
        ArrayList<Quest> quests = new ArrayList<>();
            Set<String> questIds = questsConfiguration.getConfiguration().getConfigurationSection("Quests").getKeys(false);
            for(String id: questIds){
                    quests.add(loadQuest(id));
            }
        return quests;
    }

    public Set<Quest> loadQuests(Rank rank){
        Set<String> questIds = questsConfiguration.getConfiguration().getConfigurationSection("Quests").getKeys(false);
        Set<Quest> quests = new HashSet<>();

        if(rank == null){
            return null;
        }

        for(String id: questIds){
            if(loadQuest(id).getRank().getValue() == rank.getValue()) {
                quests.add(loadQuest(id));
            }
        }
        return quests;
    }

    public Quest loadQuest(String questId){
        QuestBuilder questBuilder = new QuestBuilder(configLoader, questId);
        questBuilder.load();
        return questBuilder.build();
    }


}
