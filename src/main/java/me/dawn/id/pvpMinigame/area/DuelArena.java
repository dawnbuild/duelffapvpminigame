package me.dawn.id.pvpMinigame.area;

import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import org.bukkit.Location;

public class DuelArena {
    Location player1Spawn;
    Location player2Spawn;
    Area area;
    //TODO: Save ni data ke DuelAreaSaveFile
    public DuelArena(Area area){
        this.area = area;
    }
    public void setPlayer1Spawn(Location loc){
        this.player1Spawn=loc;
        DuelAreaFile.setLoc(area.getName(),"player1",player1Spawn);
        DuelAreaFile.save();
        DuelAreaFile.reload();
    }
    public void setPlayer2Spawn(Location loc){
        this.player2Spawn=loc;
        DuelAreaFile.setLoc(area.getName(),"player2",player2Spawn);
        DuelAreaFile.save();
        DuelAreaFile.reload();
    }

    public Location getPlayer1Spawn(){
        return player1Spawn;
    }
    public Location getPlayer2Spawn(){
        return player2Spawn;
    }
}
