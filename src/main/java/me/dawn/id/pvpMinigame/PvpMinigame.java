package me.dawn.id.pvpMinigame;

import me.dawn.id.pvpMinigame.admin.SelectionModeFunc;
import me.dawn.id.pvpMinigame.commands.admin.*;
import me.dawn.id.pvpMinigame.commands.cmdDebug;
import me.dawn.id.pvpMinigame.commands.player.KitCommand;
import me.dawn.id.pvpMinigame.commands.player.duel.DuelAcceptCommand;
import me.dawn.id.pvpMinigame.commands.player.duel.DuelCommand;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.kit.gui.KitEditorGUIFunc;
import me.dawn.id.pvpMinigame.thegame.EventGameListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PvpMinigame extends JavaPlugin {
    static PvpMinigame pl;
    @Override
    public void onEnable() {
        // Plugin startup logic
        pl = this;
        DuelAreaFile.setup();
        DuelAreaFile.save();
        DuelAreaFile.reload();

        KitFile.setup();
        KitFile.save();
        KitFile.reload();

        this.getCommand("setarea").setExecutor(new CommandSetArea());
        getCommand("debugtest").setExecutor(new cmdDebug());
        getCommand("setduelspawn").setExecutor(new CommandSetSpawn());
        getCommand("setkit").setExecutor(new CommandSetKit());
        getCommand("editkit").setExecutor(new CommandEditKit());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("pvpminigamereload").setExecutor(new CommandReload());
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand());
        getCommand("kit").setExecutor(new KitCommand());

        getCommand("duel").setTabCompleter(new DuelCommand());
        getCommand("kit").setTabCompleter(new KitCommand());
        this.getServer().getPluginManager().registerEvents(new KitEditorGUIFunc(),this);
        this.getServer().getPluginManager().registerEvents(new SelectionModeFunc(),this);
        this.getServer().getPluginManager().registerEvents(new EventGameListener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static PvpMinigame getInstance(){
        return pl;
    }
}
