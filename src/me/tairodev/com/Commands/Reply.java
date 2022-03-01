package me.tairodev.com.Commands;

import me.tairodev.com.Main;
import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Hooks.PAFHook;
import me.tairodev.com.Utils.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class Reply extends Command {

    private Main main;
    private Methods methods = new Methods();
    private ConfigUtil configUtil = new ConfigUtil();

    public Reply(Main main) {
        super("reply", "", "r");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try{
                if(player.hasPermission("bungeecore.reply")){
                    if(args.length >=1){
                        if(main.getRecentMessages().containsKey(player.getUniqueId())){
                            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(main.getRecentMessages().get(player.getUniqueId()));
                            if(configUtil.getConfig().getBoolean("Settings.use-PartyAndFriend")){
                                if(PAFHook.getPafEnabled()){
                                    if(!PAFHook.isFriendOf(player, target)){
                                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.nolonger-friend")));
                                        main.getRecentMessages().remove(player.getUniqueId());
                                        return;
                                    }
                                }
                            }
                            if(target != null){
                                StringBuilder builder = new StringBuilder();
                                for(int i = 0; i < args.length; i++){
                                    builder.append(args[i] + " ");
                                }
                                if(methods.hasMessagesDisabled(target)){
                                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-message-format")
                                            .replace("{server}", target.getServer().getInfo().getName())
                                            .replace("{target}", target.getName())
                                            .replace("{message}", builder)));

                                    target.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.receiver-message-format")
                                            .replace("{server}", player.getServer().getInfo().getName())
                                            .replace("{player}", player.getName()
                                                    .replace("{message}", builder))));
                                    return;
                                }else{
                                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-message-toggled")));
                                    return;
                                }
                            }else{
                                player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.nolonger-online")));
                                main.getRecentMessages().remove(player.getUniqueId());
                            }
                        }else{
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.noreply-messages")));
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
