// 
// Decompiled by Procyon v0.5.36
// 

package ch.party.partysystem;

import net.md_5.bungee.api.plugin.Command;
import ch.party.partysystem.command.PartyCommand;
import ch.party.partysystem.listener.ProxySwitchListener;
import net.md_5.bungee.api.plugin.Listener;
import ch.party.partysystem.listener.ProxyDisconnectListener;
import ch.party.partysystem.manager.PartyManager;
import net.md_5.bungee.api.plugin.Plugin;

public class PartySystem extends Plugin
{
    private String prefix;
    private PartyManager pm;
    
    public void onEnable() {
        this.prefix = "§bCloudVace §8| ";
        this.registerCommands();
        this.registerManagers();
        this.registerListeners();
    }
    
    private void registerListeners() {
        this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)new ProxyDisconnectListener(this));
        this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)new ProxySwitchListener(this));
    }
    
    private void registerCommands() {
        this.getProxy().getPluginManager().registerCommand((Plugin)this, (Command)new PartyCommand(this));
    }
    
    private void registerManagers() {
        this.pm = new PartyManager(this);
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public PartyManager getPartyManager() {
        return this.pm;
    }
}
