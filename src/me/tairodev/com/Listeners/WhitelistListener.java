package me.tairodev.com.Listeners;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhitelistListener implements Listener {

    private ConfigUtil configUtil = new ConfigUtil();

    @EventHandler
    public void onJoin(ServerConnectEvent event){
        ProxiedPlayer player = event.getPlayer();
        try{
            List<String> list = new ArrayList<>();
            if(configUtil.getConfig().getBoolean("Settings.whitelist-on")){
                for(String s : configUtil.getWhitelist().getStringList("Whitelist")){
                    list.add(s);
                }
                if(!list.contains(player.getDisplayName())){
                    player.disconnect(new TextComponent(ColorsAPI.colors(configUtil.getConfig().getString("Settings.whitelist-message"))));
                }
            }
        }catch (IOException x){
            x.printStackTrace();
        }

    }

}
