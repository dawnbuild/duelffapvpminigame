package me.dawn.id.pvpMinigame.commands.admin;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class CommandSetKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] arg){
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (arg.length <= 1) return MsgUtil.noArgs("%prefix% &eUsage: &6/setkit &7<&akit_name&7> &7<&aMode(&cDUEL&8/&cFFA&a)&7>",player,false);
        String kitName = arg[0];
        String mode = arg[1];
        PlayerInventory inv = player.getInventory();
        Inventory in = inv;
        List<ItemStack> items = Arrays.asList(in.getStorageContents());
        Kit kit = new Kit(kitName,items, PvpMode.valueOf(mode.toUpperCase()));
        KitFile.setArmor(kitName,inv.getItem(EquipmentSlot.HEAD),EquipmentSlot.HEAD,PvpMode.valueOf(mode));
        KitFile.setArmor(kitName,inv.getItem(EquipmentSlot.CHEST),EquipmentSlot.CHEST,PvpMode.valueOf(mode));
        KitFile.setArmor(kitName,inv.getItem(EquipmentSlot.LEGS),EquipmentSlot.LEGS,PvpMode.valueOf(mode));
        KitFile.setArmor(kitName,inv.getItem(EquipmentSlot.FEET),EquipmentSlot.FEET,PvpMode.valueOf(mode));
        KitFile.setArmor(kitName,inv.getItem(EquipmentSlot.OFF_HAND),EquipmentSlot.OFF_HAND,PvpMode.valueOf(mode));
        kit.saveKit();
        MsgUtil.sendMessage(player,"%prefix% &eKit &a" + kitName + "&e dengan mode &c"+ mode +" &aberhasil&e disimpan!");
        return true;
    }
}
