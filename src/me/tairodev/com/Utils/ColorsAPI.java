package me.tairodev.com.Utils;

import net.md_5.bungee.api.ChatColor;

public class ColorsAPI {

    public static String colors(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
