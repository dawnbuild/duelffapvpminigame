package me.dawn.id.pvpMinigame.commands.player.duel;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.thegame.Duel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String string, @NotNull String[] arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (arg.length !=3) return MsgUtil.noArgs("%prefix% &eUsage: &f/duel <&aPlayer_Name&f> <&aKit_Name&f> <&aDuel_Arena&f>",player,false);
        String target = arg[0];
        if (Bukkit.getPlayer(target) == null || !Bukkit.getPlayer(target).isOnline()) return MsgUtil.noArgs("%prefix% &cTidak ada pemain dengan nama &a" + target,player,false);
        String kitName = arg[1];
        String duelArena = arg[2];
        Duel duel = new Duel(DuelAreaFile.getAreaByName(duelArena),player,kitName);
        Duel.invite(duel, Bukkit.getPlayer(target),kitName);
        return true;
    }
}
