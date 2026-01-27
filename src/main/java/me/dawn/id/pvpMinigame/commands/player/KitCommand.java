package me.dawn.id.pvpMinigame.commands.player;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KitCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] arg) {
        if (!(sender instanceof Player)) return false;
        Player player= (Player) sender;
        if (arg.length !=1) return MsgUtil.noArgs("%prefix% &eUsage: &f/Kit <&aKit_Name&f>",player,false);
        String kitName = arg[0];
        Kit.giveKit(player,kitName, PvpMode.FFA);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> tabComplete = new ArrayList<>();
        doMyTabComplete(args,tabComplete);
        return tabComplete;
    }
    public void doMyTabComplete(String[] args, List<String> tabComplete){
        ConfigurationSection section;
        if (args.length==1){
            tabComplete.clear();
            section = DuelAreaFile.get().getConfigurationSection("Area");
            if (section.getKeys(false).isEmpty()) return;
            tabComplete.addAll(section.getKeys(false));
        }
    }
}
