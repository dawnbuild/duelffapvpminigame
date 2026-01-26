package me.dawn.id.pvpMinigame.commands.player;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] arg) {
        if (!(sender instanceof Player)) return false;
        Player player= (Player) sender;
        if (arg.length !=1) return MsgUtil.noArgs("%prefix% &eUsage: &f/Kit <&aKit_Name&f>",player,false);
        String kitName = arg[0];
        Kit.giveKit(player,kitName, PvpMode.FFA);
        return true;
    }
}
