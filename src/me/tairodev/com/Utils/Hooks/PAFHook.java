package me.tairodev.com.Utils.Hooks;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PAFHook {

    private static boolean isPafEnabled = false;

    public static boolean isFriendOf(ProxiedPlayer player, ProxiedPlayer target){
        PAFPlayerManager api = PAFPlayerManager.getInstance();
        PAFPlayer pafPlayer = api.getPlayer(target);

        return api.getPlayer(player).isAFriendOf(pafPlayer);
    }

    public static void setPafEnabled(boolean enabled){
        isPafEnabled = enabled;
    }

    public static boolean getPafEnabled(){
        return isPafEnabled;
    }


}
