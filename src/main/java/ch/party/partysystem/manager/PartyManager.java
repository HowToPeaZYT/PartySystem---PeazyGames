// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.manager;

import ch.party.partysystem.util.Invitation;
import java.util.Iterator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.ArrayList;
import ch.party.partysystem.util.Party;
import java.util.List;
import ch.party.partysystem.PartySystem;

public class PartyManager
{
    private final PartySystem plugin;
    private final List<Party> parties;
    
    public PartyManager(final PartySystem plugin) {
        this.plugin = plugin;
        this.parties = new ArrayList<Party>();
    }
    
    public void createParty(final ProxiedPlayer player) {
        this.parties.add(new Party(this.plugin, player));
    }
    
    public void invitePlayer(final ProxiedPlayer sender, final ProxiedPlayer player) {
        if (this.getParty(sender) == null) {
            this.createParty(sender);
            sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du hast eine §5Party §aerstellt!"));
        }
        final Party party = this.getParty(sender);
        if (party.getLeader().equals(sender)) {
            if (!party.getPlayers().contains(player)) {
                party.invitePlayer(player);
                sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du hast §6" + player.getName() + " in die §5Party §aeingeladen!"));
            }
            else {
                sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Dieser Spieler ist §cbereits §7in deiner §5Party!"));
            }
        }
        else {
            sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist §cnicht §7der §5Party §cLeader!"));
        }
    }
    
    public void acceptInvitation(final ProxiedPlayer player, final ProxiedPlayer leader) {
        for (final Party party : this.parties) {
            if (party.getLeader().equals(leader)) {
                final Invitation invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    party.acceptInvitation(invitation);
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du hast die §5Party §aEinladung §aangenommen!"));
                    return;
                }
                continue;
            }
        }
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du hast von diesem Spieler §ckeine §aEinladung §7erhalten!"));
    }
    
    public void denyInvitation(final ProxiedPlayer player, final ProxiedPlayer leader) {
        for (final Party party : this.parties) {
            if (party.getLeader().equals(leader)) {
                final Invitation invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "Du hast die §5Party §aEinladung §cabgelehnt!"));
                    return;
                }
                continue;
            }
        }
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du hast von diesem Spieler §ckeine §aEinladung §7erhalten!"));
    }
    
    public void kickPlayer(final ProxiedPlayer sender, final ProxiedPlayer player) {
        final Party party = this.getParty(sender);
        if (party != null) {
            if (party.getLeader().equals(sender)) {
                if (party.getPlayers().contains(player)) {
                    party.kickPlayer(player);
                }
                else {
                    sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Dieser Spieler ist §cnicht §7in deiner §5Party!"));
                }
            }
            else {
                sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist §cnicht §7der §5Party §cLeader!"));
            }
        }
        else {
            sender.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist in §ckeiner §5Party!"));
        }
    }
    
    public Party getParty(final ProxiedPlayer player) {
        for (final Party party : this.parties) {
            if (party.getPlayers().contains(player) || party.getLeader().equals(player)) {
                return party;
            }
        }
        return null;
    }
    
    public List<Party> getParties() {
        return this.parties;
    }
}
