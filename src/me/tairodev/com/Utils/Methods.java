package me.tairodev.com.Utils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class Methods extends Command {

    private static ConfigUtil configUtil = new ConfigUtil();

    private static ServerInfo serverInfo;

    public Methods() {
        super("goto");
    }

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
        serverInfo = server.getInfo();
        click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goto " + server));
        click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ColorsAPI.colors(configUtil.getMessages().getString("Messages.click-connect")))));

        player.sendMessage(new BaseComponent[]{click});
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length == 1){
            if(serverInfo == null){
                return;
            }
            ProxiedPlayer player = (ProxiedPlayer) sender;
            player.connect(serverInfo);
        }

    }
}
