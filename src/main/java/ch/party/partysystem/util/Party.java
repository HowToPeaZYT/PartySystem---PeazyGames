package ch.party.partysystem.util;

import ch.party.partysystem.PartySystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party {

    private ProxiedPlayer leader;
    private final List<ProxiedPlayer> players;
    private final Map<ProxiedPlayer, Invitation> invitations;

    private final PartySystem plugin;

    public Party(PartySystem plugin, ProxiedPlayer leader) {
        this.leader = leader;
        this.plugin = plugin;
        players = new ArrayList<>();
        invitations = new HashMap<>();
    }

    public void invitePlayer(ProxiedPlayer player) {
        if (!invitations.containsKey(player) || !players.contains(player)) {
            invitations.put(player, new Invitation(plugin, player, this, 3));
        }
    }

    public void setLeader(ProxiedPlayer player) {
        if (players.contains(player)) {
            players.add(leader);
            leader = player;
            players.remove(player);
        }
    }

    public void kickPlayer(ProxiedPlayer player) {
        removePlayer(player);
        player.sendMessage(new TextComponent(plugin.getPrefix() + "You were kicked out of the party!"));
        broadcastMessage(player.getName() + " got kicked out of the party!");
    }

    public void removePlayer(ProxiedPlayer player) {
        players.remove(player);
        if (players.isEmpty()) {
            plugin.getPartyManager().getParties().remove(this);
            broadcastMessage("The party was dissolved!");
        }
    }

    public void removeLeader() {
        plugin.getPartyManager().getParties().remove(this);
        broadcastMessage("The party was dissolved!");
    }

    public void removeInvitation(Invitation invitation) {
        invitations.remove(invitation.getPlayer());
    }

    public void acceptInvitation(Invitation invitation) {
        players.add(invitation.getPlayer());
        broadcastMessage(invitation.getPlayer().getName() + " has joined the Party!");
    }

    public Invitation getInvitation(ProxiedPlayer player) {
        return invitations.get(player);
    }

    public void broadcastMessage(String message) {
        leader.sendMessage(new TextComponent(plugin.getPrefix() + message));
        for (ProxiedPlayer player : players) {
            player.sendMessage(new TextComponent(plugin.getPrefix() + message));
        }
    }

    public ProxiedPlayer getLeader() {
        return leader;
    }

    public List<ProxiedPlayer> getPlayers() {
        return players;
    }
}
