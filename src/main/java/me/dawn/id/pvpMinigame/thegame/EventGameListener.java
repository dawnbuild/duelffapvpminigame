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
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

public class EventGameListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player loser = e.getPlayer();
        Player winner = e.getEntity();
        if (Duel.getDuelBy(winner,loser) == null) return;
        if (!Duel.isPlayersDueling(loser,winner)) return;
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        BukkitScheduler bt = Bukkit.getScheduler();
        for (int i =1;i<=5;i++){
            bt.runTaskLater(PvpMinigame.getInstance(),()->{
                spawnFireworks(winner.getLocation().add(2,0,0));
                spawnFireworks(winner.getLocation().add(0,0,2));
                spawnFireworks(winner.getLocation().add(0,0,-2));
                spawnFireworks(winner.getLocation().add(-2,0,0));
            }, 20L);
        }
        Duel.getDuelBy(loser,winner).stop();
        MsgUtil.sendMessage(loser,"%prefix% &eDuel telah berakhir");
        MsgUtil.sendMessage(loser,"%prefix% &eDuel kali ini dimenangkan oleh &a" + winner.getName());
        MsgUtil.sendMessage(loser,"%prefix% &eDengan sisa darah &8[&a" + winner.getHealth() + "&c❤&8]");
    }

    public static void spawnFireworks(Location loc){
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
        Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        fw2.setFireworkMeta(fwm);
    }
}
