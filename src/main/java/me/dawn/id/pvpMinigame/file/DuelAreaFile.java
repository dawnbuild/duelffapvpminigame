package me.dawn.id.pvpMinigame.file;

import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.area.DuelArena;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DuelAreaFile {

    private static File file;
    private static FileConfiguration customFile;
    public static String AREA="Area.";

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(PvpMinigame.getInstance().getDataFolder(), "DuelArea.yml");

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

    public static void setLoc(String areaName,String pos, Location loc){
        get().set(AREA +areaName+'.'+ pos,loc);
    }

    public static Area getAreaByName(String areaName){
        return new Area(areaName,get().getLocation(AREA + areaName+".pos1"),get().getLocation(AREA + areaName+".pos1"));
    }

    public static DuelArena getDuelAreaSpawnByName(String areaName){
        DuelArena das = new DuelArena(getAreaByName(areaName));
        das.setPlayer1Spawn(get().getLocation(AREA +areaName+".player1"));
        das.setPlayer2Spawn(get().getLocation(AREA +areaName+".player2"));
        return das;
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
