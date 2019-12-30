package ch.party.partysystem;

import ch.party.partysystem.command.PartyCommand;
import ch.party.partysystem.listener.ProxyDisconnectListener;
import ch.party.partysystem.listener.ProxySwitchListener;
import ch.party.partysystem.manager.PartyManager;
import net.md_5.bungee.api.plugin.Plugin;

public class PartySystem extends Plugin {

    private String prefix;
    private PartyManager pm;

    @Override
    public void onEnable() {
        prefix = "§7[§5Party§7]§r ";
        registerCommands();
        registerManagers();
        registerListeners();
    }

    private void registerListeners() {
        this.getProxy().getPluginManager().registerListener(this, new ProxyDisconnectListener(this));
        this.getProxy().getPluginManager().registerListener(this, new ProxySwitchListener(this));
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new PartyCommand(this));
    }

    private void registerManagers() {
        pm = new PartyManager(this);
    }

    public String getPrefix() {
        return prefix;
    }

    public PartyManager getPartyManager() {
        return pm;
    }
}
