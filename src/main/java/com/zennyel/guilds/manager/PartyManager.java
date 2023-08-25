package com.zennyel.guilds.manager;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.Party;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyManager {

    private Map<Player, Party> partyHashMap;
    private Map<Player, Party> partyInvited;
    private QuestManager questManager;
    private AdventurerManager adventurerManager;
    public PartyManager(GuildsPlugin guildsPlugin) {
        partyHashMap = new HashMap<>();
        partyInvited = new HashMap<>();
        this.questManager = guildsPlugin.getQuestCache();
        this.adventurerManager = guildsPlugin.getCache();
    }

    public void exitParty(Player player){
        if(getPartyOfPlayer(player) != null){
            getPartyOfPlayer(player).getMembers().remove(player);
            updateParty(player);
            partyHashMap.put(player, null);

        }
    }

    // Create a new party with the given owner and members
    public void createParty(Player owner, List<Player> members) {
        Party party = new Party(members, owner);
        partyHashMap.put(owner, party);
    }

    // Invite a player to a party
    public void inviteToParty(Player sender, Player invitedPlayer) {
        Party party = getPartyOfPlayer(sender);
        if (party != null) {
            partyInvited.put(invitedPlayer, party);
        }
    }

    public void completePartyMission(Player player){
        if(getPartyOfPlayer(player) != null){
            for(Player players : getPartyOfPlayer(player).getMembers()){
                if(questManager.getQuest(players) == null) {
                    return;
                }
                if(questManager.getQuest(player) != questManager.getQuest(players)){
                    return;
                }
                questManager.finalizeQuest(players, adventurerManager.getAdventurer(players));
            }
        }
    }

    // Accept a party invitation
    public void acceptPartyInvitation(Player player) {
        Party party = partyInvited.get(player);
        if (party != null) {
            partyHashMap.put(player, party);
            partyInvited.put(player, null);
            getPartyOfPlayer(player).getMembers().add(player);
            updateParty(player);
        }
    }

    // Decline a party invitation
    public void declinePartyInvitation(Player player) {
        partyInvited.remove(player);
    }

    // Get the party a player is a member of
    public Party getPartyOfPlayer(Player player) {
        return partyHashMap.get(player);
    }

    // Get pending invitations for a player
    public Party getInvitationOfPlayer(Player player) {
        return partyInvited.get(player);
    }


    public void updateParty(Player player){
        if(getPartyOfPlayer(player) == null){
            return;
        }

        for(Player players : getPartyOfPlayer(player).getMembers()){
            partyHashMap.put(players, getPartyOfPlayer(player));
        }

    }

    // Add a new invitation for a player
    public void addInvitation(Player player, Party party) {
        partyInvited.put(player, party);
    }
}
