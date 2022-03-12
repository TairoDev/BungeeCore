package me.tairodev.com.Listeners;

import me.tairodev.com.Main;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessageListener implements Listener {

    private Main main;

    public MessageListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event){

        if(main.getRecentMessages().containsKey(event.getPlayer().getUniqueId())){
            main.getRecentMessages().remove(event.getPlayer().getUniqueId());
        }

    }

}
