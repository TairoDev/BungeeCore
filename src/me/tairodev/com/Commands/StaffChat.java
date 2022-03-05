package me.tairodev.com.Commands;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Hooks.LuckPermsHook;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StaffChat extends Command {

    private static ArrayList<UUID> members = new ArrayList<>();

    private static HashMap<UUID, StringBuilder> membersMessage = new HashMap<>();

    private ConfigUtil configUtil = new ConfigUtil();

    public StaffChat() {
        super("staffchat", null, "sc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try{
                if(player.hasPermission("bungeecore.staffchat")){
                    if(args.length == 0){
                        if(!members.contains(player.getUniqueId())){
                            members.add(player.getUniqueId());
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-toggle")
                            .replace("{0}", configUtil.getMessages().getString("Messages.enabled"))));
                        }else{
                            members.remove(player.getUniqueId());
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-toggle")
                            .replace("{0}", configUtil.getMessages().getString("Messages.disabled"))));
                        }
                    }else if(args.length >= 1){
                        StringBuilder builder = new StringBuilder();
                        for(int i = 0; i < args.length; i++){
                            builder.append(args[i] + " ");
                        }
                        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                            if(all.hasPermission("bungeecore.staffchat")){
                                if(LuckPermsHook.getLuckPermsStatus()){
                                    all.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-msg")
                                            .replace("{server}", player.getServer().getInfo().getName())
                                            .replace("{prefix}", LuckPermsHook.getPrefix(player))
                                            .replace("{suffix}", LuckPermsHook.getSuffix(player))
                                            .replace("{player}", player.getName())
                                            .replace("{message}", builder)));
                                }else{
                                    all.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.staffchat-msg")
                                            .replace("{server}", player.getServer().getInfo().getName())
                                            .replace("{player}", player.getName())
                                            .replace("{message}", builder)));
                                }
                            }
                        }
                    }
                    configUtil.getConfig();
                }else{
                    player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.no-permissions")));
                }
            }catch (IOException x){
                x.printStackTrace();
            }

        }

    }

    public static boolean isSCMember(ProxiedPlayer player){
        return members.contains(player.getUniqueId());
    }

    public static StringBuilder getMemberMessage(ProxiedPlayer player){
        return membersMessage.get(player.getUniqueId());
    }

}
