package me.dawn.id.pvpMinigame.admin;

import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.MsgUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionMode {
    private static final Map<UUID, Area> map = new HashMap<>();

    public static void addSelectionMode(Player player,Area area){
        map.put(player.getUniqueId(),area);
        MsgUtil.sendMessage(player,"%prefix% &7Memasuki Mode Memilih &bArea");
        MsgUtil.sendMessage(player,"%prefix% &7Silahkan &dLeft Click &7untuk memilih &aPosition 1");
        MsgUtil.sendMessage(player,"%prefix% &7Silahkan &dRight Click &7untuk memilih &aPosition 2");

    }

    public static void removeSelectionMode(Player player){
        MsgUtil.sendMessage(player,"%perfix% &cKeluar &7dari mode memilih &bArea");
        map.remove(player.getUniqueId());
    }

    public static Area getArea(Player player){
        return map.get(player.getUniqueId());
    }

    public static boolean isInSelMode(Player player){
        return map.containsKey(player.getUniqueId());
    }
}
