// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.listener;

import net.md_5.bungee.event.EventHandler;
import ch.party.partysystem.util.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import ch.party.partysystem.PartySystem;
import net.md_5.bungee.api.plugin.Listener;

public class ProxyDisconnectListener implements Listener
{
    private final PartySystem plugin;
    
    public ProxyDisconnectListener(final PartySystem plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onDisconnect(final PlayerDisconnectEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        final Party party = this.plugin.getPartyManager().getParty(player);
        if (party != null) {
            if (party.getLeader().equals(player)) {
                party.removeLeader();
            }
            else {
                party.removePlayer(player);
            }
        }
    }
}
