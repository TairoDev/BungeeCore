package me.tairodev.com.Commands;

import me.tairodev.com.Utils.ColorsAPI;
import me.tairodev.com.Utils.ConfigUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhiteList extends Command {

    private ConfigUtil configUtil = new ConfigUtil();

    public WhiteList() {
        super("whitelist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try{
            if(sender.hasPermission("bungeecore.whitelist")) {
                if (args.length == 0) {
                    for (String s : configUtil.getMessages().getStringList("Messages.whitelist-usage")) {
                        sender.sendMessage(ColorsAPI.colors(s));
                    }
                    return;
                } else if (args.length == 1) {
                    if (args[0].equals("on") || args[0].equals("enable")) {
                        if (!configUtil.getConfig().getBoolean("Settings.whitelist-on")) {
                            configUtil.getConfig().set("Settings.whitelist-on", true);
                            configUtil.saveConfig();
                            sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-toggling")
                                    .replace("{0}", configUtil.getMessages().getString("Messages.enabled"))));
                        } else {
                            sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-already-state")
                                    .replace("{0}", configUtil.getMessages().getString("Messages.enabled"))));
                        }
                    } else if (args[0].equals("off") || args[0].equals("disable")) {
                        if (configUtil.getConfig().getBoolean("Settings.whitelist-on")) {
                            configUtil.getConfig().set("Settings.whitelist-on", false);
                            configUtil.saveConfig();
                            sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-toggling")
                                    .replace("{0}", configUtil.getMessages().getString("Messages.disabled"))));
                        } else {
                            sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-already-state")
                                    .replace("{0}", configUtil.getMessages().getString("Messages.disabled"))));
                        }
                    } else if (args[0].equals("status")) {
                        for (String s : configUtil.getMessages().getStringList("Messages.whitelist-status")) {
                            if (configUtil.getConfig().getBoolean("Settings.whitelist-on")) {
                                sender.sendMessage(ColorsAPI.colors(s.replace("{0}", configUtil.getMessages().getString("Messages.enabled"))
                                        .replace("{1}", String.valueOf(configUtil.getWhitelist().getStringList("Whitelist").size()))));
                            } else {
                                sender.sendMessage(ColorsAPI.colors(s.replace("{0}", configUtil.getMessages().getString("Messages.disabled"))
                                        .replace("{1}", String.valueOf(configUtil.getWhitelist().getStringList("Whitelist").size()))));
                            }
                        }
                    } else if (args[0].equals("add")) {
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.playername-missing")));
                    } else if (args[0].equals("remove")) {
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.playername-missing")));
                    } else if (args[0].equals("setmessage")) {
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.wlmessage-missing")));
                    }
                } else if (args.length == 2) {
                    if (args[0].equals("add")) {
                        List<String> list = new ArrayList<>();
                        for (String s : configUtil.getWhitelist().getStringList("Whitelist")) {
                            if (s.equals(args[1])) {
                                sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.already-whitelisted")
                                        .replace("{player}", args[1])));
                                return;
                            }
                            list.add(s);
                            list.add(args[1]);
                        }
                        configUtil.getWhitelist().set("Whitelist", list);
                        configUtil.saveWhitelist();
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-added")
                                .replace("{player}", args[1])));
                    } else if (args[0].equals("remove")) {
                        List<String> list = new ArrayList<>();
                        for (String s : configUtil.getWhitelist().getStringList("Whitelist")) {
                            list.add(s);
                            if (list.contains(args[1])) {
                                list.remove(args[1]);
                                configUtil.getWhitelist().set("Whitelist", list);
                                configUtil.saveWhitelist();
                                sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.whitelist-removed")
                                        .replace("{player}", args[1])));
                                return;
                            }
                        }
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.notyet-whitelisted")
                                .replace("{player}", args[1])));
                    } else if (args[0].equals("setmessage")) {
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.wlmessage-error")));
                        return;
                    }
                } else {
                    if (args[0].equals("setmessage")) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i] + " ");
                        }
                        configUtil.getConfig().set("Settings.whitelist-message", String.valueOf(builder));
                        configUtil.saveConfig();
                        sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.wlmessage-updated")));
                        return;
                    }
                }
            }else {
                sender.sendMessage(ColorsAPI.colors(configUtil.getMessages().getString("Messages.no-permissions")));
            }

        }catch (IOException x){
            x.printStackTrace();
        }

    }

}
