package me.dawn.id.pvpMinigame.kit;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.file.playerdata.FFAPlayerKitData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class Kit {
    String kitName;
    List<ItemStack> items;
    PvpMode kitMode;
    public Kit(String kitName,List<ItemStack> items,PvpMode kitMode){
        this.kitName = kitName;
        this.items =items;
        this.kitMode = kitMode;
    }

    public static void giveKit(Player player, String name,PvpMode mode){
        if (mode == PvpMode.FFA && FFAPlayerKitData.getFFAKill(player) != KitFile.getFFAKillReq(name)){
            MsgUtil.noArgs("%prefix% &cKamu belum membuka akses untuk kit ini",player,false);
            MsgUtil.noArgs("%prefix% &cKamu memerlukan &a" + KitFile.getFFAKillReq(name) + " &cKill dari mode &aFFA",player,false);
            return;
        }
        if (!KitFile.isKitExist(name,mode)){
            MsgUtil.noArgs("%prefix% &cKit dengan nama &a" + name +" &tidak ditemukan!",player,false);
            return;
        }
        PlayerInventory pinv = player.getInventory();
        pinv.clear();
        pinv.setHelmet(KitFile.getArmorNOffhand(name,mode, EquipmentSlot.HEAD));
        pinv.setChestplate(KitFile.getArmorNOffhand(name,mode, EquipmentSlot.CHEST));
        pinv.setLeggings(KitFile.getArmorNOffhand(name,mode, EquipmentSlot.LEGS));
        pinv.setBoots(KitFile.getArmorNOffhand(name,mode, EquipmentSlot.FEET));
        pinv.setItemInOffHand(KitFile.getArmorNOffhand(name,mode, EquipmentSlot.OFF_HAND));
        ItemStack[] i = KitFile.getItems(name, mode).toArray(new ItemStack[0]);
        pinv.setStorageContents(i);
        MsgUtil.sendMessage(player,"%prefix% &bMengenakan kit &a" + name);
    }

    public void saveKit(){
        KitFile.setKits(kitName,items,kitMode);
    }
}
