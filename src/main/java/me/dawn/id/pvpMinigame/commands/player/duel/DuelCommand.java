package me.dawn.id.pvpMinigame.commands.player.duel;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.thegame.Duel;
import org.bukkit.Bukkit;
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

public class DuelCommand implements CommandExecutor, TabCompleter {
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> tabComplete = new ArrayList<>();
        doMyTabComplete(args,tabComplete);
        return tabComplete;
    }

    public void doMyTabComplete(String[] args, List<String> tabComplete){
        ConfigurationSection section;
        switch (args.length){
            case 1:
                tabComplete.clear();
                for(Player player : Bukkit.getOnlinePlayers()){
                    tabComplete.add(player.getName());
                }
                break;
            case 2:
                tabComplete.clear();
                section = KitFile.get().getConfigurationSection("PlayerKit.DUEL");
                for(String kitname : section.getKeys(false)){
                    tabComplete.add(kitname);
                }
                break;
            case 3:
                tabComplete.clear();
                section = DuelAreaFile.get().getConfigurationSection("Area");
                for(String kitname : section.getKeys(false)){
                    tabComplete.add(kitname);
                }
                break;
            default:
                break;
        }
    }
}
