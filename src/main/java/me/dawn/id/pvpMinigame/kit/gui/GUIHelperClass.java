package me.dawn.id.pvpMinigame.kit.gui;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMinigame;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class GUIHelperClass {
    /*
     * Just an ordinary Item Builder
     * */
    public static ItemStack item(String name,NamespacedKey namespaceKey, Material material, List<String> lore, boolean glow){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(namespaceKey, PersistentDataType.BOOLEAN,true);
        if (glow){
            item.addUnsafeEnchantment(Enchantment.AQUA_AFFINITY,1);
            meta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
        }
        if (lore !=null) meta.setLore(lore);
        meta.setItemName(MsgUtil.ChatColor(name));
        item.setItemMeta(meta);
        return item;
    }

    /*
     * Give item a single function like delete or save or rename kit when clicked
     * */
    public static ItemStack funcionalItem(ItemStack item,String task,String NSKEY){
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(PvpMinigame.getInstance(),NSKEY+"func");
        meta.getPersistentDataContainer().set(nk,PersistentDataType.STRING,task);
        item.setItemMeta(meta);
        return item;
    }

    /*
     * Getting item function like(Save Kit/Delete Kit) when item clicked from inventory
     * */
    public static boolean getItemFunc(ItemStack item,String func,String NSKEY){
        NamespacedKey nsk = NamespacedKey.fromString(NSKEY + "func",PvpMinigame.getInstance());
        assert nsk != null;
        return item.getItemMeta().getPersistentDataContainer().get(nsk,PersistentDataType.STRING) == func;
    }

    /*
     * Checking if an item is an moveable item or not
     * */
    public static boolean isItemMoveable(ItemStack item,String NSKEY){
        NamespacedKey nsk = NamespacedKey.fromString(NSKEY,PvpMinigame.getInstance());
        assert nsk != null;
        ItemMeta meta = item.getItemMeta();
        return (meta.getPersistentDataContainer().has(nsk) && meta.getPersistentDataContainer().getOrDefault(nsk,PersistentDataType.BOOLEAN,true));
    }
}
