// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem.command;

import java.util.Iterator;
import ch.party.partysystem.util.Party;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.CommandSender;
import ch.party.partysystem.PartySystem;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command
{
    private final PartySystem plugin;
    
    public PartyCommand(final PartySystem plugin) {
        super("party");
        this.plugin = plugin;
    }
    
    public void execute(final CommandSender cs, final String[] args) {
        if (!(cs instanceof ProxiedPlayer)) {
            cs.sendMessage((BaseComponent)new TextComponent("Only players can use this command!"));
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer)cs;
        if (args.length > 1 && args[0] != null && args[0].equalsIgnoreCase("chat")) {
            final Party party = this.plugin.getPartyManager().getParty(player);
            if (party != null) {
                final StringBuilder sb = new StringBuilder();
                for (int amount = 1; amount < args.length; ++amount) {
                    sb.append(args[amount]).append(" ");
                }
                party.broadcastMessage(player.getName() + " §7» §7" + sb.toString());
            }
            else {
                player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist in §ckeiner §5Party!"));
            }
            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                final Party party = this.plugin.getPartyManager().getParty(player);
                if (party != null) {
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "- §7Deine §5Party -"));
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§cLeader§7: §6" + party.getLeader().getName()));
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Mitglieder: "));
                    for (final ProxiedPlayer others : party.getPlayers()) {
                        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7- §6" + others.getName()));
                    }
                }
                else {
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist in §ckeiner §5Party!"));
                }
                return;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                final Party party = this.plugin.getPartyManager().getParty(player);
                if (party != null) {
                    if (party.getLeader().equals(player)) {
                        party.removeLeader();
                    }
                    else {
                        party.removePlayer(player);
                    }
                }
                else {
                    player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Du bist in §ckeiner §5Party!"));
                }
                return;
            }
        }
        else if (args.length == 2) {
            final ProxiedPlayer other = this.plugin.getProxy().getPlayer(args[1]);
            if (other == null) {
                player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7Dieser Spieler existiert §cnicht!"));
                return;
            }
            if (args[0].equalsIgnoreCase("invite")) {
                this.plugin.getPartyManager().invitePlayer(player, other);
                return;
            }
            if (args[0].equalsIgnoreCase("accept")) {
                this.plugin.getPartyManager().acceptInvitation(player, other);
                return;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                this.plugin.getPartyManager().denyInvitation(player, other);
                return;
            }
            if (args[0].equalsIgnoreCase("kick")) {
                this.plugin.getPartyManager().kickPlayer(player, other);
                return;
            }
        }
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§7========== §5Party §cCommands §7=========="));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party list"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party leave"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party invite <Spieler>"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party accept <Spieler>"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party deny <Spieler>"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party kick <Spieler>"));
        player.sendMessage((BaseComponent)new TextComponent(this.plugin.getPrefix() + "§f/party chat <Nachricht>"));
    }
}
