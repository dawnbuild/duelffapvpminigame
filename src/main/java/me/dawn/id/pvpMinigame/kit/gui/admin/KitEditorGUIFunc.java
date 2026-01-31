package me.dawn.id.pvpMinigame.kit.gui.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.KitFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitEditorGUIFunc implements Listener {
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        Inventory inv = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        if (inv == null || inv.getHolder() == null) return;
        if (!(inv.getHolder() instanceof KitEditorGUI kitEditorGUI))return;
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) return;
        if (!KitEditorGUI.isItemMoveable(clickedItem)) return;
        e.setCancelled(true);
        if (KitEditorGUI.getItemFunc(clickedItem,"deletekit")){
            KitFile.removeKit(kitEditorGUI.getKitName(),kitEditorGUI.getMode());
            player.closeInventory();
            MsgUtil.sendMessage(player,"%prefix% &cKit berhasil di hapus!!");
        }else if (KitEditorGUI.getItemFunc(clickedItem,"savekit")){
            String kitName = kitEditorGUI.getKitName();
            PvpMode mode = kitEditorGUI.getMode();
            KitFile.setArmor(kitName,inv.getItem(0), EquipmentSlot.HEAD,mode);
            KitFile.setArmor(kitName,inv.getItem(1), EquipmentSlot.CHEST,mode);
            KitFile.setArmor(kitName,inv.getItem(2), EquipmentSlot.LEGS,mode);
            KitFile.setArmor(kitName,inv.getItem(3), EquipmentSlot.FEET,mode);
            KitFile.setArmor(kitName,inv.getItem(5), EquipmentSlot.OFF_HAND,mode);
//        System.out.println(kitName +" " + mode.toString());
            List<ItemStack> items = Arrays.asList(inv.getStorageContents());
            List<ItemStack> is = new ArrayList<>();
            for (int i = 18;i <items.size();i++){
                is.add(items.get(i));
            }
            KitFile.setKits(kitName,is,mode);
            player.closeInventory();
            MsgUtil.sendMessage(player,"%prefix% &cKit berhasil di simpan!!");
        }

    }
}
