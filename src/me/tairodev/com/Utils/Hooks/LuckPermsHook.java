package me.tairodev.com.Utils.Hooks;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class LuckPermsHook {

    private static boolean isLuckPermsEnabled = false;

    public static String getPrefix(ProxiedPlayer player){
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getPlayerAdapter(ProxiedPlayer.class).getUser(player);

        if(user.getCachedData().getMetaData().getPrefix() != null){
            return user.getCachedData().getMetaData().getPrefix();
        }else{
            return "[no-prefix-found]";
        }
    }

    public static String getSuffix(ProxiedPlayer player){
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getPlayerAdapter(ProxiedPlayer.class).getUser(player);

        if(user.getCachedData().getMetaData().getSuffix() != null){
            return user.getCachedData().getMetaData().getSuffix();
        }else{
            return "[no-suffix-found]";
        }
    }

    public static boolean getLuckPermsStatus(){
        return isLuckPermsEnabled;
    }

    public static void setLuckPermsStatus(boolean newStatus){
        isLuckPermsEnabled = newStatus;
    }


}
