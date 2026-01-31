package me.dawn.id.pvpMinigame.file.playerdata;

import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.area.DuelArena;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FFAPlayerKitData {

    private static File file;
    private static FileConfiguration customFile;
    public static String PLAYERDATA="PlaerData.";

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(PvpMinigame.getInstance().getDataFolder(), "FFA_PlayerData.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        get().set(PLAYERDATA,null);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static boolean isPlayerNew(Player player){
        return get().getString(PLAYERDATA + player.getName() + ".UUID") != null;
    }

    public static void newPlayer(Player player){
        get().set(PLAYERDATA + player.getName() + ".UUID",player.getUniqueId());
        get().set(PLAYERDATA + player.getName() + ".FFA_KILL",0);
        get().set(PLAYERDATA + player.getName() + ".DUEL_KILL",0);
        get().set(PLAYERDATA + player.getName() + ".UNLOCKED_KIT",null);
    }

    public static int getFFAKill(Player player){
        return get().getInt(PLAYERDATA + player.getName() + ".FFA_KILL");
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
