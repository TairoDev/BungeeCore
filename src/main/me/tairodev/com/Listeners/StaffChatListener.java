package me.tairodev.com.Listeners;

import me.tairodev.com.Commands.StaffChat;
import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Hooks.LuckPermsHook;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;

public class StaffChatListener implements Listener {

    private ConfigUtil configUtil = new ConfigUtil();

    @EventHandler
    public void onChat(ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if(StaffChat.isSCMember(player)){
            if(event.isCommand()){
                return;
            }
            event.setCancelled(true);
            for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
                if(players.hasPermission("bungeecore.staffchat")){
                    try {
                        if(LuckPermsHook.getLuckPermsStatus()){
                            players.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-msg")
                            .replace("{server}", player.getServer().getInfo().getName())
                            .replace("{prefix}", LuckPermsHook.getPrefix(player))
                            .replace("{suffix}", LuckPermsHook.getSuffix(player))
                            .replace("{player}", player.getName())
                            .replace("{message}", event.getMessage())));
                        }else{
                            players.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-msg")
                            .replace("{server}", player.getServer().getInfo().getName())
                            .replace("{player}", player.getName())
                            .replace("{message}", event.getMessage())));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }

}