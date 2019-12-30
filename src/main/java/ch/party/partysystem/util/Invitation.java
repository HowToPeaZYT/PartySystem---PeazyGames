package ch.party.partysystem.util;

import ch.party.partysystem.PartySystem;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class Invitation {

    private final ProxiedPlayer player;
    private final Party party;
    private final PartySystem plugin;
    private boolean valid;

    private ScheduledTask task;

    public Invitation(PartySystem plugin, ProxiedPlayer player, Party party, int minutes) {
        this.player = player;
        this.party = party;
        this.plugin = plugin;
        valid = true;
        startInvitation(minutes);
    }

    private void acceptInvitation() {
        if (valid) {
            task.cancel();
            party.removeInvitation(this);
            party.acceptInvitation(this);
            player.sendMessage(new TextComponent("You accepted the party request!"));
        }
    }

    private void denyInvitation() {
        if (valid) {
            task.cancel();
            party.removeInvitation(this);
            player.sendMessage(new TextComponent("You declined the party request!"));
        }
    }

    private void startInvitation(int minutes) {
        player.sendMessage(new TextComponent(plugin.getPrefix() + "You were invited to a party by " + party.getLeader().getName() + "!\n§aAccept§7: §a/party accept <name> §8| §cDeny§7: §c/party deny <name>"));
        task = plugin.getProxy().getScheduler().schedule(plugin, () -> {
            party.removeInvitation(this);
            valid = false;
        }, minutes, TimeUnit.MINUTES);
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }
}
