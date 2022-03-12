package me.tairodev.com.Commands;

import me.tairodev.com.Main;
import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Hooks.LuckPermsHook;
import me.tairodev.com.Utils.Hooks.PAFHook;
import me.tairodev.com.Utils.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Message extends Command implements TabExecutor {

    private Main main;
    private Methods methods = new Methods();
    private ConfigUtil configUtil = new ConfigUtil();

    public Message(Main main) {
        super("message", "", "msg");
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try{
                if(player.hasPermission("bungeecore.message")){
                    if(args.length == 0){
                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.pmessages-usage")));
                        return;
                    }
                    if(args.length == 1){
                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.message-missing")));
                        LuckPermsHook.getPrefix(player);
                        return;
                    }
                    if(args.length == 2){
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                        if(target == null){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-not-found").replace("{player}", args[0])));
                            return;
                        }
                        if(configUtil.getConfig().getBoolean("Settings.use-PartyAndFriend")){
                            if(PAFHook.getPafEnabled()){
                                if(!PAFHook.isFriendOf(player, target)){
                                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.paf-addon")));
                                    return;
                                }
                            }
                        }
                        StringBuilder builder = new StringBuilder();
                        for(int i = 1; i < args.length; i++){
                            builder.append(args[i] + " ");
                        }
                        if(player == target){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.write-yourself")));
                            return;
                        }
                        if(methods.hasMessagesDisabled(target)){

                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-message-format")
                                    .replace("{server}", target.getServer().getInfo().getName())
                                    .replace("{target}", target.getName())
                                            .replace("{message}", builder)));

                            target.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.receiver-message-format")
                                    .replace("{server}", player.getServer().getInfo().getName())
                                    .replace("{player}", player.getName())
                                            .replace("{message}", builder)));

                            main.getRecentMessages().put(player.getUniqueId(), target.getUniqueId());
                            main.getRecentMessages().put(target.getUniqueId(), player.getUniqueId());
                            return;
                        }else{
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-message-toggled")));
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

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if(sender instanceof ProxiedPlayer){
            if(args.length == 2){
                if(args[0].equals("msg") || args[0].equals("message")){
                    for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                        results.add(String.valueOf(all));
                    }
                }
            }
        }

        return results;
    }
}
