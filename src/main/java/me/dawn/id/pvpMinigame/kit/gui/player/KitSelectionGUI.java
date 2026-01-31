package me.dawn.id.pvpMinigame.kit.gui.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class KitSelectionGUI implements InventoryHolder {
    /*
    * TODO: Buat kit hanya bisa di gunkana untuk jumlah kill tertentu
    * */
    Inventory gui;
    public KitSelectionGUI(Player player){
        gui = Bukkit.createInventory(this,9*6,"Select Kit");

    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }
}
