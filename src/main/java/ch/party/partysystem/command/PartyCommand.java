package ch.party.partysystem.command;

import ch.party.partysystem.PartySystem;
import ch.party.partysystem.util.Party;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command {

    private final PartySystem plugin;

    public PartyCommand(PartySystem plugin) {
        super("party");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (!(cs instanceof ProxiedPlayer)) {
            cs.sendMessage(new TextComponent("Only players can use this command!!"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) cs;
        if (args.length > 2 && args[0] != null && args[0].equalsIgnoreCase("chat")) {
            Party party = plugin.getPartyManager().getParty(player);
            if (party != null) {
                StringBuilder sb = new StringBuilder();
                for (int amount = 1; amount < args.length; amount++) {
                    sb.append(args[amount]).append(" ");
                }
                party.broadcastMessage(player.getName() + " §8» §7" + sb.toString());
            } else {
                player.sendMessage(new TextComponent(plugin.getPrefix() + "You are not in a party!"));
            }
            return;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                Party party = plugin.getPartyManager().getParty(player);
                if (party != null) {
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "- Party Informations -"));
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "Leader: " + party.getLeader().getName()));
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "Players: "));
                    for (ProxiedPlayer others : party.getPlayers()) {
                        player.sendMessage(new TextComponent(plugin.getPrefix() + "- " + others.getName()));
                    }
                } else {
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "You are not in a party!"));
                }
                return;
            } else if (args[0].equalsIgnoreCase("leave")) {
                Party party = plugin.getPartyManager().getParty(player);
                if (party != null) {
                    if (party.getLeader().equals(player)) {
                        party.removeLeader();
                    } else {
                        party.removePlayer(player);
                    }
                } else {
                    player.sendMessage(new TextComponent(plugin.getPrefix() + "You are not in a party!"));
                }
                return;
            }
        } else if (args.length == 2) {
            ProxiedPlayer other = plugin.getProxy().getPlayer(args[1]);
            if (other != null) {
                if (args[0].equalsIgnoreCase("invite")) {
                    plugin.getPartyManager().invitePlayer(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    plugin.getPartyManager().acceptInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("deny")) {
                    plugin.getPartyManager().denyInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    plugin.getPartyManager().kickPlayer(player, other);
                    return;
                }
            } else {
                player.sendMessage(new TextComponent(plugin.getPrefix() + "This player don't exists!"));
                return;
            }
        }
        player.sendMessage(new TextComponent(plugin.getPrefix() + "- Party Management -"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party list"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party leave"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party invite <Player>"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party accept <Player>"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party deny <Player>"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party kick <Player>"));
        player.sendMessage(new TextComponent(plugin.getPrefix() + "/party chat <Message>"));
    }
}
