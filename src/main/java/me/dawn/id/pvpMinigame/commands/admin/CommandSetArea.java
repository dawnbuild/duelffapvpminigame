package me.dawn.id.pvpMinigame.commands.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.admin.SelectionMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetArea implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (SelectionMode.isInSelMode(player)){
            SelectionMode.removeSelectionMode(player);
            return false;
        }
        if (arg.length != 1) return MsgUtil.noArgs("%prefix% &eUsage: &f/setarea areaname",player,false);
        String areaName = arg[0];
        Area area = new Area(areaName,null,null);
        SelectionMode.addSelectionMode(player,area);
        return true;
    }
}
