package me.dawn.id.pvpMinigame.commands.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        DuelAreaFile.reload();
        DuelAreaFile.save();
        KitFile.reload();
        KitFile.save();
        MsgUtil.sendMessage(sender,"%prefix% &aPlugin berhasil di reload!");
        return true;
    }
}
