package me.dawn.id.pvpMinigame.thegame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventGameListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player loser = e.getPlayer();
        Player winner = e.getEntity();
        if (Duel.getDuelBy(winner,loser) == null) return;
        if (!Duel.isPlayersDueling(loser,winner)) return;
        Duel.getDuelBy(loser,winner).stop();
        e.setKeepInventory(true);
        e.setKeepLevel(true);
    }

}
