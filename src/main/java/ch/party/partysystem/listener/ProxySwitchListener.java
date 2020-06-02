// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.listener;

import net.md_5.bungee.event.EventHandler;
import java.util.Iterator;
import ch.party.partysystem.util.Party;
import net.md_5.bungee.api.plugin.Plugin;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import ch.party.partysystem.PartySystem;
import net.md_5.bungee.api.plugin.Listener;

public class ProxySwitchListener implements Listener
{
    private final PartySystem plugin;
    
    public ProxySwitchListener(final PartySystem plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onSwitch(final ServerSwitchEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        final Party party = this.plugin.getPartyManager().getParty(player);
        if (party != null && party.getLeader().equals(player) && !party.getLeader().getServer().getInfo().getName().contains("lobby-1")) {
            final Party party2;
            ProxiedPlayer other;
            final ProxiedPlayer proxiedPlayer;
            final TextComponent textComponent;
            final Object o;
            this.plugin.getProxy().getScheduler().schedule((Plugin)this.plugin, () -> {
                party2.getPlayers().iterator();
                final Iterator<ProxiedPlayer> iterator;
                while (iterator.hasNext()) {
                    other = iterator.next();
                    if (!other.getServer().getInfo().equals(proxiedPlayer.getServer().getInfo())) {
                        other.connect(proxiedPlayer.getServer().getInfo());
                        new TextComponent(this.plugin.getPrefix() + "§7Die §5Party §7hat einen neuen §bServer §abetreten!");
                        ((ProxiedPlayer)o).sendMessage((BaseComponent)textComponent);
                    }
                }
            }, 1L, TimeUnit.SECONDS);
        }
    }
}
