package me.tairodev.com.Utils;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.io.IOException;

public class Methods {

    private static ConfigUtil configUtil = new ConfigUtil();

    public boolean hasMessagesDisabled(ProxiedPlayer player) throws IOException {

        if(configUtil.getData().contains("player-data." + player.getUniqueId())){
            if(configUtil.getData().getBoolean("player-data." + player.getUniqueId())){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public static void reportText(ProxiedPlayer player, Server server) throws IOException{

        TextComponent click = new TextComponent(ColorsAPI.colors(configUtil.getMessages().getString("Messages.click-here"))
        .replace("{server}", server.getInfo().getName()));
        click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + server.getInfo().getName()));
        click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ColorsAPI.colors(configUtil.getMessages().getString("Messages.click-connect")))));

        player.sendMessage(new BaseComponent[]{click});
    }


}
