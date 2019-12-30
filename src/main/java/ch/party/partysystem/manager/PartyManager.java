package ch.party.partysystem.manager;

import ch.party.partysystem.PartySystem;
import ch.party.partysystem.util.Invitation;
import ch.party.partysystem.util.Party;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {

    private final PartySystem plugin;
    private final List<Party> parties;

    public PartyManager(PartySystem plugin) {
        this.plugin = plugin;
        parties = new ArrayList<>();
    }

    public void createParty(ProxiedPlayer player) {
        parties.add(new Party(plugin, player));
    }

    public void invitePlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        if (getParty(sender) == null) {
            createParty(sender);
            sender.sendMessage(new TextComponent(plugin.getPrefix() + "You have created an new party!"));
        }
        Party party = getParty(sender);
        if (party.getLeader().equals(sender)) {
            if (!party.getPlayers().contains(player)) {
                party.invitePlayer(player);
                sender.sendMessage(new TextComponent(plugin.getPrefix() + "§aYou have invited " + player.getName() + " succesfully!"));
            } else {
                sender.sendMessage(new TextComponent(plugin.getPrefix() + "§cThis player is already in your party!"));
            }
        } else {
            sender.sendMessage(new TextComponent(plugin.getPrefix() + "§cYou are not the party leader!"));
        }
    }

    public void acceptInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invitation invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    party.acceptInvitation(invitation);
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "§aYou accepted the party request!"));
                    return;
                }
            }
        }
        player.sendMessage(new TextComponent(plugin.getPrefix() + "§cYou don't have any requests of this player!"));
    }

    public void denyInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invitation invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "You declined the party request!"));
                    return;
                }
            }
        }
        player.sendMessage(new TextComponent(plugin.getPrefix() + "You don't have any requests of this player!"));
    }

    public void kickPlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        Party party = getParty(sender);
        if (party != null) {
            if (party.getLeader().equals(sender)) {
                if (party.getPlayers().contains(player)) {
                    party.kickPlayer(player);
                } else {
                    sender.sendMessage(new TextComponent(plugin.getPrefix() + "This player is not in your party!"));
                }
            } else {
                sender.sendMessage(new TextComponent(plugin.getPrefix() + "You are not the party leader!"));
            }
        } else {
            sender.sendMessage(new TextComponent(plugin.getPrefix() + "You are not in a party!"));
        }
    }

    public Party getParty(ProxiedPlayer player) {
        for (Party party : parties) {
            if (party.getPlayers().contains(player) || party.getLeader().equals(player)) {
                return party;
            }
        }
        return null;
    }

    public List<Party> getParties() {
        return parties;
    }
}
