package me.dawn.id.pvpMinigame.kit.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class KitSelcetionGUI implements InventoryHolder {
    /*
    * TODO: Buat kit hanya bisa di gunkana untuk jumlah kill tertentu
    * */
    Inventory gui;
    public KitSelcetionGUI(Player player){
        gui = Bukkit.createInventory(this,9,"Select Kit");
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }
}
