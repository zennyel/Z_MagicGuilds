package com.zennyel.guilds.command;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.Party;
import com.zennyel.guilds.manager.AdventurerManager;
import com.zennyel.guilds.manager.PartyManager;
import com.zennyel.guilds.manager.QuestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class PartyCommand extends AbstractCommand {

    private PartyManager partyManager;
    private QuestManager questManager;
    private AdventurerManager adventurerManager;

    public PartyCommand(GuildsPlugin guildsPlugin) {
        this.partyManager = guildsPlugin.getPartyCache();
        this.questManager = guildsPlugin.getQuestCache();
        this.adventurerManager = guildsPlugin.getCache();
    }

    @Override
    public boolean handleCommands(CommandSender sender, Command command, String s, String[] args) {
        super.handleCommands(sender, command, s, args);

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use party commands.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("zmagic.use")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return false;
        }

        if (!command.getName().equalsIgnoreCase("party")) {
            return false;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /party <create|invite|acceptinvite|declineinvite|members|exit|disband>");
            return true;
        }


        if(questManager.getQuest(player) != null){
            player.sendMessage(ChatColor.RED + "Finish your active quest first!");
            return false;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "disband":
                disbandParty(player);
                break;
            case "exit":
                exitParty(player);
                break;
            case "create":
                createParty(player);
                break;
            case "invite":
                inviteToParty(player, args);
                break;
            case "acceptinvite":
                acceptPartyInvitation(player);
                break;
            case "declineinvite":
                declinePartyInvitation(player);
                break;
            case "members":
                listPartyMembers(player);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Invalid subcommand. Available subcommands: create, invite, acceptinvite, declineinvite, members, exit, disband");
        }

        return true;
    }

    private void createParty(Player player) {
        ArrayList<Player> members = new ArrayList();
        members.add(player);
        partyManager.createParty(player, members);
        player.sendMessage(ChatColor.GREEN + "You created a new party!");
    }

    private void inviteToParty(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage: /party invite <player>");
            return;
        }

        Player invitedPlayer = player.getServer().getPlayer(args[1]);
        if (invitedPlayer == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        if(questManager.getQuest(invitedPlayer) != null){
            player.sendMessage(ChatColor.RED + "The invited player must finish their actual quest before entering a party.");
            return;
        }

        if (invitedPlayer.equals(player)) {
            player.sendMessage(ChatColor.RED + "You cannot invite yourself to your own party.");
            return;
        }

        Party party = partyManager.getPartyOfPlayer(player);
        if (party == null) {
            player.sendMessage(ChatColor.RED + "You are not in a party.");
            return;
        }

        partyManager.inviteToParty(player, invitedPlayer);
        player.sendMessage(ChatColor.GREEN + "Invited " + invitedPlayer.getName() + " to your party.");
        invitedPlayer.sendMessage(ChatColor.YELLOW + "You have been invited to " + player.getName() + "'s party. Use '/party acceptinvite' or '/party declineinvite'.");
    }

    private void acceptPartyInvitation(Player player) {
        Party invitationParty = partyManager.getInvitationOfPlayer(player);

        if (invitationParty != null) {
            partyManager.acceptPartyInvitation(player);
            player.sendMessage(ChatColor.GREEN + "Successfully joined " + invitationParty.getOwner().getName() + "'s party!");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have any pending party invitations.");
        }
    }

    private void declinePartyInvitation(Player player) {
        Party invitationParty = partyManager.getInvitationOfPlayer(player);

        if (invitationParty != null) {
            partyManager.declinePartyInvitation(player);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have any pending party invitations.");
        }
    }

    private void listPartyMembers(Player player) {
        Party party = partyManager.getPartyOfPlayer(player);
        if (party == null) {
            player.sendMessage(ChatColor.RED + "You are not in a party.");
        } else {
            List<Player> members = party.getMembers();
            player.sendMessage(ChatColor.YELLOW + "Party Members:");
            for (Player member : members) {
                player.sendMessage(ChatColor.YELLOW + " - " + member.getName());
            }
        }
    }


    public void exitParty(Player player){
        if(partyManager.getPartyOfPlayer(player) == null){
            player.sendMessage("§cYou don't have a party!");
            return;
        }

        Party party = partyManager.getPartyOfPlayer(player);
        if(party.getOwner() != player){
            player.sendMessage("§eLeaved " + party.getOwner().getName() + "'s party!");
            partyManager.exitParty(player);
            for(Player member : party.getMembers()){
                member.sendMessage("§e" + player.getName() + " leaved the party!");
            }
        }else{
            player.sendMessage("§cYou are the owner, you cannot leave the party!");
            player.sendMessage("§cinstead, use: /party disband to dissolve your party.");
        }
    }
    public void disbandParty(Player owner){
        if(partyManager.getPartyOfPlayer(owner) == null){
        owner.sendMessage("§cYou don't have a party!");
        return;
    }

        Party party = partyManager.getPartyOfPlayer(owner);
        if(party.getOwner().equals(owner)){
            owner.sendMessage("§eParty disbanded :(");
            for(Player player : party.getMembers()){
                partyManager.exitParty(player);
                if(player != owner){
                    player.sendMessage("§e" + owner.getName() + " disbanded the party! :( ");
                }
            }
        }else{
            owner.sendMessage("§cYou are not the party owner!");
        }
    }



}
