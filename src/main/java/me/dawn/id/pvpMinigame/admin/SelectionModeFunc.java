package me.dawn.id.pvpMinigame.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionModeFunc implements Listener {
    /*
    * Jika Admin berada pada mode selection
    * */
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (!SelectionMode.isInSelMode(player)) return;
        assert e.getClickedBlock() != null;
        e.setCancelled(true);
        switch (e.getAction()){
            case RIGHT_CLICK_BLOCK:
                SelectionMode.getArea(player).setPos2(e.getClickedBlock().getLocation());
                MsgUtil.sendMessage(player,"%prefix% &bPos2 &7telah dipilih");
                break;
            case LEFT_CLICK_BLOCK:
                SelectionMode.getArea(player).setPos1(e.getClickedBlock().getLocation());
                MsgUtil.sendMessage(player,"%prefix% &bPos1 &7telah dipilih");
                break;
        }
    }

    /*
    * Ketika Admin berada pada mode selection dan mengirim pesan "leave" dan "keluar"
    * akan membatalkan mode selection
    * pesan "done" akan menyimpan data dari object Area ke file yaml
    * */
    @EventHandler
    public void onPlayerChat(PlayerChatEvent e){
        Player player = e.getPlayer();
        if (!SelectionMode.isInSelMode(player)) return;
        e.setCancelled(true);
        String msg = e.getMessage();
        if (msg.equalsIgnoreCase("leave") || msg.equalsIgnoreCase("keluar")) SelectionMode.removeSelectionMode(player);
        else if (msg.equalsIgnoreCase("done")){
            if (SelectionMode.getArea(player).isPosNull()){
                MsgUtil.sendMessage(player,"%prefix% &7Kamu &cBelum &7Memilih Area Minigame!!");
                MsgUtil.sendMessage(player,"%prefix% &7Jika ingin keluar dari mode &aSelect Area &7kirimi pesan &cleave &7atau &ckeluar");
                return;
            }
            SelectionMode.getArea(player).saveArea();
            SelectionMode.removeSelectionMode(player);
            MsgUtil.sendMessage(player,"&aArea telah disimpan!!");
        }
    }
}
