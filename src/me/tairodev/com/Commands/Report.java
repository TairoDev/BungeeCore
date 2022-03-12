package me.tairodev.com.Commands;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Report extends Command implements TabExecutor {

    private HashMap<UUID, UUID> already = new HashMap<>();

    private ConfigUtil configUtil = new ConfigUtil();

    public Report() {
        super("report");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            try {
                if (player.hasPermission("bungeecore.report")) {
                    if(args.length == 0){
                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.report-usage")));
                        return;
                    }else if(args.length == 1){
                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.reason-missing")));
                        return;
                    }else{
                        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                        StringBuilder builder = new StringBuilder();
                        for(int i = 1; i < args.length; i++){
                            builder.append(args[i] + " ");
                        }
                        if(target == null){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.player-not-found")
                                    .replace("{player}", args[0])));
                            return;
                        }
                        if(target == player){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.report-yourself")));
                            return;
                        }
                        if(target.hasPermission("bungeecore.staffchat")){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.report-bypass")));
                            return;
                        }
                        if(already.get(player.getUniqueId()) == target.getUniqueId()){
                            player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.already-reported")));
                            return;
                        }
                        player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.report-done")
                                .replace("{player}", target.getName())
                                .replace("{reason}", builder)));
                        already.put(player.getUniqueId(), target.getUniqueId());
                        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                            if(all.hasPermission("bungeecore.seereports")){
                                for(String s : configUtil.getMessages().getStringList("Messages.staff-report-format")){
                                    all.sendMessage(ColorsAPI.colors(s)
                                            .replace("{player}", player.getName())
                                            .replace("{target}", target.getName())
                                            .replace("{reason}", builder)
                                            .replace("{server}", target.getServer().getInfo().getName()));
                                }
                                if(configUtil.getConfig().getBoolean("Settings.use-reportgoto")){
                                    Methods.reportText(all, target.getServer());
                                }
                            }
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
            if(args.length == 0){
                if(args[0].equals("report")){
                    for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                        results.add(all.toString());
                    }
                }
            }
        }

        return results;
    }
}
