package me.dawn.id.pvpMinigame.commands.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.area.DuelArena;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) return MsgUtil.noArgs("%prefix% &eUsage: &f/setduelspawn <area_name> <player&a1&f/&c2>",player,false);
        String areaName = args[0];
        String playerSpawn = args[1];
        DuelArena das = new DuelArena(DuelAreaFile.getAreaByName(areaName));
        if (playerSpawn.equalsIgnoreCase("player1")) das.setPlayer1Spawn(player.getLocation());
        else if (playerSpawn.equalsIgnoreCase("player2")) das.setPlayer2Spawn(player.getLocation());
        else return MsgUtil.noArgs("%prefix% &eUsage: &f/setduelspawn <area_name> <player&a1&f/&c2>",player,false);;
        MsgUtil.sendMessage(player,"%prefix% &eSpawn point untuk area duel &a" + areaName + " &etelah di buat!");
        return true;
    }
}
