package me.dawn.id.pvpMinigame.commands;

import me.dawn.id.pvpMinigame.thegame.Duel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class cmdDebug implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!commandSender.isOp()) return false;
        System.out.println("Known duel object: " + Arrays.toString(Duel.inviteMap.entrySet().toArray()));
        System.out.println("Known oldObject: " + Arrays.toString(Duel.oldData.entrySet().toArray()));
        return true;
    }
}
