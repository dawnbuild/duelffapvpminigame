package me.dawn.id.pvpMinigame.kit.gui.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.KitFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.dawn.id.pvpMinigame.kit.gui.GUIHelperClass.funcionalItem;
import static me.dawn.id.pvpMinigame.kit.gui.GUIHelperClass.item;

public class KitEditorGUI implements InventoryHolder {
    public static final int GUI_SIZE = 6;
    static final String NSKEY = "kiteditoritem";
    static NamespacedKey namespaceKey = new NamespacedKey(PvpMinigame.getInstance(), NSKEY);
    String kitName;
    PvpMode mode;

    public KitEditorGUI(String kitName, PvpMode mode){
        this.kitName=kitName;
        this.mode=mode;
    }

    public static void openEditor(Player player,String str,PvpMode pvpMode){
        if (!KitFile.isKitExist(str,pvpMode)){
            KitFile.setKits(str,new ArrayList<ItemStack>(),pvpMode);
            KitFile.setArmor(str,null, EquipmentSlot.HEAD,pvpMode);
            KitFile.setArmor(str,null, EquipmentSlot.CHEST,pvpMode);
            KitFile.setArmor(str,null, EquipmentSlot.LEGS,pvpMode);
            KitFile.setArmor(str,null, EquipmentSlot.FEET,pvpMode);
            KitFile.setArmor(str,null, EquipmentSlot.OFF_HAND,pvpMode);
        }
        Inventory inventory = new KitEditorGUI(str,pvpMode).inv();
        player.openInventory(inventory);
    }

    public String getKitName(){
        return kitName;
    }

    /*
    * Build an inventory and store all of content into it
    * */
    public Inventory inv(){
        Inventory inv = Bukkit.createInventory(this,GUI_SIZE*9, ChatColor.translateAlternateColorCodes('&',kitName));
        ItemStack armor = item("&aArmor Slot",namespaceKey,Material.ORANGE_STAINED_GLASS_PANE,null,true);
        ItemStack offhand = item("&bOffhand Slot",namespaceKey,Material.LIGHT_BLUE_STAINED_GLASS_PANE,null,true);
        ItemStack nothin = item("",namespaceKey,Material.BLACK_STAINED_GLASS_PANE,null,false);
        ItemStack delete = funcionalItem(item("&l&cDelete Kit",namespaceKey,Material.REDSTONE,null,false),"deletekit",NSKEY);
        ItemStack save = funcionalItem(item("&l&aSave Kit",namespaceKey,Material.EMERALD,null,false),"savekit",NSKEY);
        inv.setItem(4,nothin);
        inv.setItem(6,nothin);
        inv.setItem(7,delete);
        inv.setItem(8,save);
        for (int i = 9;i<=17;i++){
            if (i<=12){
                inv.setItem(i,armor);
                continue;
            }
            if (i==14){
                inv.setItem(i,offhand);
                continue;
            }
            inv.setItem(i,nothin);
        }
        ItemStack[] a = KitFile.getItems(kitName, mode).toArray(new ItemStack[0]);
        if (a.length!=0){
            for (int i = 18;i <=53;i++){
                int j = i-18;
                inv.setItem(i,a[j]);
            }
        }
        inv.setItem(0,KitFile.getArmorNOffhand(kitName,mode,EquipmentSlot.HEAD));
        inv.setItem(1,KitFile.getArmorNOffhand(kitName,mode,EquipmentSlot.CHEST));
        inv.setItem(2,KitFile.getArmorNOffhand(kitName,mode,EquipmentSlot.LEGS));
        inv.setItem(3,KitFile.getArmorNOffhand(kitName,mode,EquipmentSlot.FEET));
        inv.setItem(5,KitFile.getArmorNOffhand(kitName,mode,EquipmentSlot.OFF_HAND));
        return inv;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv();
    }

    public PvpMode getMode() {
        return mode;
    }
}
