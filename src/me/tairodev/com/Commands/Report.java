package me.tairodev.com.Commands;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import me.tairodev.com.Utils.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Report extends Command {

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
                        for(String s : configUtil.getConfig().getStringList("ReportSystem.bypass-players")){
                            if(s.equals(target.getName())){
                                player.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.report-bypass")));
                                return;
                            }
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
                                    Methods.reportText(player, target.getServer());
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

}
