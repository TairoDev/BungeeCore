package me.tairodev.com;


import me.tairodev.com.Commands.*;
import me.tairodev.com.Listeners.MessageListener;
import me.tairodev.com.Listeners.StaffChatListener;
import me.tairodev.com.Listeners.WhitelistListener;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.DataHandler;
import me.tairodev.com.Utils.Hooks.LuckPermsHook;
import me.tairodev.com.Utils.Hooks.PAFHook;
import me.tairodev.com.Utils.Methods;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends Plugin {

    private static Main instance;

    private HashMap<UUID, UUID> recentMessages;

    private DataHandler dataHandler = new DataHandler();

    private ConfigUtil configUtil;

    @Override
    public void onEnable() {
        instance = this;

        this.dataHandler.Files();
        this.registerCommands();
        this.registerListener();

        recentMessages = new HashMap<>();

        if(ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms") != null) {
            LuckPermsHook.setLuckPermsStatus(true);
        }

        if(ProxyServer.getInstance().getPluginManager().getPlugin("PartyAndFriends") != null){
            PAFHook.setPafEnabled(true);
        }else{
            configUtil = new ConfigUtil();
            try {
                if(configUtil.getConfig().getBoolean("Settings.use-PartyAndFriend")){
                    configUtil.getConfig().set("Settings.use-PartyAndFriend", false);
                    configUtil.saveConfig();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void registerCommands(){
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MSGToggle(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Message(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Reply(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChat());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Report());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Hub());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new WhiteList());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Methods());
    }

    private void registerListener(){
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MessageListener(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new StaffChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new WhitelistListener());
    }

    public static Main getInstance(){
        return instance;
    }

    public HashMap<UUID, UUID> getRecentMessages(){
        return recentMessages;
    }

}
