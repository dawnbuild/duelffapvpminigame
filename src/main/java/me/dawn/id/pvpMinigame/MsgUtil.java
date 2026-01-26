package me.dawn.id.pvpMinigame;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MsgUtil {
    public static String PLUGIN_PREFIX = "&8[&ePvpMinigame&8]&r";

    public static void sendMessage(Player player, String str){
        player.sendMessage(MsgUtil.ChatColor(str.replaceAll("%prefix%",PLUGIN_PREFIX)));
    }

    public static String ChatColor(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }

    public static boolean noArgs(String str,Player player, boolean b){
        sendMessage(player,str);
        return b;
    }
}
