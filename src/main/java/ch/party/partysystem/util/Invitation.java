// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.util;

import net.md_5.bungee.api.plugin.Plugin;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import ch.party.partysystem.PartySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Invitation
{
    private final ProxiedPlayer player;
    private final Party party;
    private final PartySystem plugin;
    private boolean valid;
    private ScheduledTask task;
    
    public Invitation(final PartySystem plugin, final ProxiedPlayer player, final Party party, final int minutes) {
        this.player = player;
        this.party = party;
        this.plugin = plugin;
        this.valid = true;
        this.startInvitation(minutes);
    }
    
    private void acceptInvitation() {
        if (this.valid) {
            this.task.cancel();
            this.party.removeInvitation(this);
            this.party.acceptInvitation(this);
            this.player.sendMessage((BaseComponent)new TextComponent("§7Du hast die §5Party §aEinladung angenommen!"));
        }
    }
    
    private void denyInvitation() {
        if (this.valid) {
            this.task.cancel();
            this.party.removeInvitation(this);
            this.player.sendMessage((BaseComponent)new TextComponent("§7Du hast die §5Party §aEinladung §cabgelehnt!"));
        }
    }
    
    private void startInvitation(final int minutes) {
        this.player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du wurdest von §6" + this.party.getLeader().getName() + " §7in eine §5Party §aeingeladen!\n§a§lAkzeptieren§r§7: §f/party accept <name> §8| §c§lAblehnen§r§7: §f/party deny <name>"));
        this.task = this.plugin.getProxy().getScheduler().schedule((Plugin)this.plugin, () -> {
            this.party.removeInvitation(this);
            this.valid = false;
        }, (long)minutes, TimeUnit.MINUTES);
    }
    
    public ProxiedPlayer getPlayer() {
        return this.player;
    }
}
