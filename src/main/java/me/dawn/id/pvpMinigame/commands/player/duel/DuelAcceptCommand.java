package me.dawn.id.pvpMinigame.commands.player.duel;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.thegame.Duel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DuelAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length !=1) return MsgUtil.noArgs("%prefix% &eUsage: &f/duelaccept <&aPlayer_Name&f>",player,false);
        String invite = args[0];
        if (Bukkit.getPlayer(invite) == null || !Bukkit.getPlayer(invite).isOnline()) return MsgUtil.noArgs("%prefix% &cTidak ada pemain dengan nama &a" + invite,player,false);
        Duel duel = Duel.getInvitation(Bukkit.getPlayer(invite),player);
        if (duel == null) return MsgUtil.noArgs("%prefix% &cTidak ada undangan duel dari &a" + invite,player,false);

        Duel.accept(duel,player);

        return true;
    }
}
