package me.dawn.id.pvpMinigame.thegame;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMinigame;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

public class EventGameListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Duel duel = Duel.getDuelBy(player);
        if (duel == null) return;
        duel.setWinner(Duel.getOpponent(player));
        duel.stop();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player loser = e.getPlayer();
        if (!(e.getDamageSource().getCausingEntity() instanceof Player)) return;
        Player winner = (Player) e.getDamageSource().getCausingEntity();
        if (!Duel.isPlayersDueling(loser,winner)) return;
        e.setKeepInventory(true);
        e.setKeepLevel(true);

        Duel.getDuelBy(loser,winner).setWinner(winner);
        Duel.getDuelBy(loser,winner).stop();
        MsgUtil.sendMessage(loser,"%prefix% &eDuel telah berakhir");
        MsgUtil.sendMessage(loser,"%prefix% &eDuel kali ini dimenangkan oleh &a" + winner.getName());
        MsgUtil.sendMessage(loser,"%prefix% &eDengan sisa darah &8[&a" + winner.getHealth() + "&c❤&8]");
    }


}
