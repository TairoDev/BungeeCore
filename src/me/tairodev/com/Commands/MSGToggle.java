package me.tairodev.com.Commands;

import me.tairodev.com.Main;
import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class MSGToggle extends Command {

    private final Main plugin = Main.getInstance();

    private Main main;

    private ConfigUtil configUtil = new ConfigUtil();

    public MSGToggle(Main main) {
        super("msgtoggle");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try{
                if(player.hasPermission("bungeecore.msgtoggle")){
                    if(args.length == 0){
                        if(configUtil.getData().contains("player-data." + player.getUniqueId())){
                            if(configUtil.getData().getBoolean("player-data." + player.getUniqueId())){
                                configUtil.getData().set("player-data." + player.getUniqueId(), false);
                                configUtil.savePlayerData();
                                player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.toggling-messages")
                                        .replace("{0}", configUtil.getMessages().getString("Messages.disabled"))));
                                return;
                            }else{
                                configUtil.getData().set("player-data." + player.getUniqueId(), true);
                                configUtil.savePlayerData();
                                player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.toggling-messages")
                                        .replace("{0}", configUtil.getMessages().getString("Messages.enabled"))));
                                return;
                            }
                        }else{
                            configUtil.getData().set("player-data." + player.getUniqueId(), false);
                            configUtil.savePlayerData();
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.toggling-messages")
                                    .replace("{0}", configUtil.getMessages().getString("Messages.disabled"))));
                            return;
                        }
                    }
                }else{
                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.no-permissions")));
                }
            }catch (IOException x){
                x.printStackTrace();
            }
        }

    }
}
