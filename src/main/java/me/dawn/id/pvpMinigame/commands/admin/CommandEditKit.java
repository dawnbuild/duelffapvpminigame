package me.dawn.id.pvpMinigame.commands.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.kit.gui.KitEditorGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandEditKit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (arg.length != 2) return MsgUtil.noArgs("%prefix% &7Usage: &e/editkit &7<&fMode&8[&cDUEL&7/&cFFA&8]&7> &7<&fKit_Name&7>",player,false);
        String mode = arg[0];
        String kitName = arg[1];
        KitEditorGUI.openEditor(player,kitName, PvpMode.valueOf(mode));
        return true;
    }
}
