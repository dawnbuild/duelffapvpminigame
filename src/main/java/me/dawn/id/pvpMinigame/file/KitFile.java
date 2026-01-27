package me.dawn.id.pvpMinigame.file;

import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.area.Area;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitFile {

    private static File file;
    private static FileConfiguration customFile;
    public static String KIT="PlayerKit.";

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(PvpMinigame.getInstance().getDataFolder(), "PlayerKit.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void removeKit(String kitName,PvpMode mode){
        get().set(KIT+mode.toString()+'.'+kitName,null);
        save();
        reload();
    }

    public static boolean isKitExist(String kitName,PvpMode mode){
        return get().contains(KIT+mode.toString()+'.'+kitName);
    }

    public static void setArmor(String kitName, ItemStack item, EquipmentSlot es,PvpMode mode){
        get().set(KIT+mode.toString()+'.'+kitName+"." + es.toString(),item);
        save();
        reload();
    }

    public static void setKits(String kitName, List<ItemStack> items, PvpMode mode){
        get().set(KIT+mode.toString()+'.'+kitName+".items",items);
        save();
        reload();
    }

    public static ItemStack getArmorNOffhand(String kitName,PvpMode mode,EquipmentSlot es){
        return get().getItemStack(KIT+mode.toString()+'.'+kitName+'.'+es.toString());
    }
    public static List<ItemStack> getItems(String kitName,PvpMode mode){
        List<ItemStack> items = (List<ItemStack>) get().getList(KIT+mode.toString()+'.'+kitName+".items");
        return items;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
