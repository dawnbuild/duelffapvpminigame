package me.dawn.id.pvpMinigame.thegame;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class OldLocNInv {
    Location loc;
    ItemStack[] armors;
    ItemStack[] contents;
    public OldLocNInv(Location loc, ItemStack[] armors,ItemStack[] contents){
        this.loc=loc;
        this.armors=armors;
        this.contents=contents;
    }
    public Location getLoc(){
        return loc;
    }

    public ItemStack[] getArmors() {
        return armors;
    }

    public ItemStack[] getContents() {
        return contents;
    }
}
