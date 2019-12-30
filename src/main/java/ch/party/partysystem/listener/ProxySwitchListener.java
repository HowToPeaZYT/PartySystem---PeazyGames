package ch.party.partysystem.listener;

import ch.party.partysystem.PartySystem;
import ch.party.partysystem.util.Party;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxySwitchListener implements Listener {
    
    private final PartySystem plugin;
    
    public ProxySwitchListener(PartySystem plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer player = e.getPlayer();
        Party party = plugin.getPartyManager().getParty(player);
        if (party != null) {
            if (party.getLeader().equals(player)) {
                if (!party.getLeader().getServer().getInfo().getName().contains("lobby-1")) {
                    plugin.getProxy().getScheduler().schedule(plugin, () -> {
                        for (ProxiedPlayer other : party.getPlayers()) {
                            if (!other.getServer().getInfo().equals(player.getServer().getInfo())) {
                                other.connect(player.getServer().getInfo());
                                other.sendMessage(new TextComponent(plugin.getPrefix() + "The party has entered a new server!"));
                            }
                        }
                    }, 1, TimeUnit.SECONDS);
                }
            }
        }
    }
}
