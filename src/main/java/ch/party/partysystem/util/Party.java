// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.util;

import java.util.Iterator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.HashMap;
import java.util.ArrayList;
import ch.party.partysystem.PartySystem;
import java.util.Map;
import java.util.List;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party
{
    private ProxiedPlayer leader;
    private final List<ProxiedPlayer> players;
    private final Map<ProxiedPlayer, Invitation> invitations;
    private final PartySystem plugin;
    
    public Party(final PartySystem plugin, final ProxiedPlayer leader) {
        this.leader = leader;
        this.plugin = plugin;
        this.players = new ArrayList<ProxiedPlayer>();
        this.invitations = new HashMap<ProxiedPlayer, Invitation>();
    }
    
    public void invitePlayer(final ProxiedPlayer player) {
        if (!this.invitations.containsKey(player) || !this.players.contains(player)) {
            this.invitations.put(player, new Invitation(this.plugin, player, this, 3));
        }
    }
    
    public void setLeader(final ProxiedPlayer player) {
        if (this.players.contains(player)) {
            this.players.add(this.leader);
            this.leader = player;
            this.players.remove(player);
        }
    }
    
    public void kickPlayer(final ProxiedPlayer player) {
        this.removePlayer(player);
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du wurdest aus der §5Party §cgekickt!"));
        this.broadcastMessage("§6" + player.getName() + " §7wurde aus der §5Party §cgekickt!");
    }
    
    public void removePlayer(final ProxiedPlayer player) {
        this.players.remove(player);
        if (this.players.isEmpty()) {
            this.plugin.getPartyManager().getParties().remove(this);
            this.broadcastMessage("§7Die §5Party §7wurde §caufgelöst!");
        }
    }
    
    public void removeLeader() {
        this.plugin.getPartyManager().getParties().remove(this);
        this.broadcastMessage("§7Die §5Party §7wurde §caufgelöst!");
    }
    
    public void removeInvitation(final Invitation invitation) {
        this.invitations.remove(invitation.getPlayer());
    }
    
    public void acceptInvitation(final Invitation invitation) {
        this.players.add(invitation.getPlayer());
        this.broadcastMessage(invitation.getPlayer().getName() + " §7ist der §5Party §abeigetreten!");
    }
    
    public Invitation getInvitation(final ProxiedPlayer player) {
        return this.invitations.get(player);
    }
    
    public void broadcastMessage(final String message) {
        this.leader.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + message));
        for (final ProxiedPlayer player : this.players) {
            player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + message));
        }
    }
    
    public ProxiedPlayer getLeader() {
        return this.leader;
    }
    
    public List<ProxiedPlayer> getPlayers() {
        return this.players;
    }
}
