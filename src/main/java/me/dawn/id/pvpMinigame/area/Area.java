package me.dawn.id.pvpMinigame.area;

import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import org.bukkit.Location;

public class Area {
    String name;
    Location pos1,pos2;
    public Area(String name, Location pos1,Location pos2){
        this.name=name;
        this.pos1=pos1;
        this.pos2=pos2;
    }

    public String getName(){
        return this.name;
    }

    public void setPos1(Location pos1){
        this.pos1=pos1;
    }

    public void setPos2(Location pos2){
        this.pos2=pos2;
    }

    public void saveArea(){
        DuelAreaFile.setLoc(name,"pos1",pos1);
        DuelAreaFile.setLoc(name,"pos2",pos2);
        DuelAreaFile.save();
        DuelAreaFile.reload();
    }

    public boolean isPosNull(){
        return (pos1  == null || pos2 == null);
    }
}
