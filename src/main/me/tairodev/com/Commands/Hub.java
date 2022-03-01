package me.tairodev.com.Commands;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Hub extends Command {

    private ConfigUtil configUtil = new ConfigUtil();
    private int random;

    public Hub() {
        super("hub", null, "lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try{
                if(player.hasPermission("bungeecore.hub")){
                    List<String> list = new ArrayList<>();
                    for(String s : configUtil.getConfig().getStringList("Settings.Hubs")){
                        list.add(s);
                    }
                    random = ThreadLocalRandom.current().nextInt(0, list.size());
                    ServerInfo server = ProxyServer.getInstance().getServerInfo(list.get(random));
                    player.connect(server);
                    if(player.getServer().getInfo() == server){
                        return;
                    }
                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.teleporting-done")
                    .replace("{server}", server.getName())));
                }else{
                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.no-permissions")));
                }
            }catch (IOException x){
                x.printStackTrace();
            }
        }

    }
}
