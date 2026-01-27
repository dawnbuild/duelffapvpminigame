package me.dawn.id.pvpMinigame.thegame;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Duel {
    Area area;
    Player player1,player2;
    String kitName;
    static  Map<UUID, OldLocNInv> oldData = new HashMap<>();
    static Map<Duel, Set<UUID>> inviteMap = new ConcurrentHashMap<>();
    DuelAreaState state;
    public Duel(Area area, Player player1, String kit){
        this.area=area;
        this.player1=player1;
        this.kitName = kit;
    }
    public DuelAreaState getState(){return state;}
    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}
    public String getKitName(){return kitName;}
    public void setPlayer2(Player player2){
        this.player2=player2;
    }
    public void setDuelAreaState(DuelAreaState s){
        this.state=s;
    }
    public void start(){
        inviteMap.get(this).clear();
        inviteMap.get(this).add(player1.getUniqueId());
        inviteMap.get(this).add(player2.getUniqueId());
        oldData.put(player1.getUniqueId(),new OldLocNInv(player1.getLocation(),player1.getInventory().getArmorContents(),player1.getInventory().getContents()));
        oldData.put(player2.getUniqueId(),new OldLocNInv(player2.getLocation(),player2.getInventory().getArmorContents(),player2.getInventory().getContents()));
        Kit.giveKit(this.getPlayer1(),this.getKitName(), PvpMode.DUEL);
        Kit.giveKit(this.getPlayer2(),this.getKitName(),PvpMode.DUEL);
        this.setDuelAreaState(DuelAreaState.CLOSED);
        getPlayer1().teleport(DuelAreaFile.getDuelAreaSpawnByName(area.getName()).getPlayer1Spawn());
        getPlayer2().teleport(DuelAreaFile.getDuelAreaSpawnByName(area.getName()).getPlayer2Spawn());
    }

    public void stop(){
        this.setDuelAreaState(DuelAreaState.OPEN);
        Bukkit.broadcastMessage("Arena " + area.getName() +" Opened!!");
        player1.teleport(oldData.get(player1.getUniqueId()).getLoc());
        player1.getInventory().setContents(oldData.get(player1.getUniqueId()).getContents());
        player1.getInventory().setArmorContents(oldData.get(player1.getUniqueId()).getArmors());
        player2.teleport(oldData.get(player2.getUniqueId()).getLoc());
        player2.getInventory().setContents(oldData.get(player2.getUniqueId()).getContents());
        player2.getInventory().setArmorContents(oldData.get(player2.getUniqueId()).getArmors());
        oldData.remove(player1.getUniqueId());
        oldData.remove(player2.getUniqueId());
        inviteMap.get(this).clear();
        inviteMap.clear();
    }

    /*TODO: Refactor this code for readable!*/
    public static boolean isPlayersDueling(Player player,Player player2){
        for (Duel duel : inviteMap.keySet())
            if (Duel.inviteMap.get(duel).size() == 2
                    && Duel.inviteMap.get(duel).contains(player.getUniqueId())
                    && Duel.inviteMap.get(duel).contains(player2.getUniqueId())) return true;
        return false;
    }
    public static Duel getDuelBy(Player player,Player player2){
        for (Duel duel : inviteMap.keySet())
            if (Duel.inviteMap.get(duel).size() == 2
                    && Duel.inviteMap.get(duel).contains(player.getUniqueId())
                    && Duel.inviteMap.get(duel).contains(player2.getUniqueId())) return duel;
        return null;
    }
    /*TODO: ^^^^Refactor this code for readable!^^^^*/


    public static Duel getInvitation(String invite,Player pAcc){
        for (Duel duel : inviteMap.keySet())
            if (inviteMap.containsKey(duel)
                    && inviteMap.get(duel).contains(pAcc.getUniqueId())
                    && duel.getPlayer1().getName().equalsIgnoreCase(invite)) return duel;
        return null;
    }

    /* TODO: Triple player safe*/
    public static void invite(Duel invitator,Player target, String kit){
        if (Duel.isPlayersDueling(invitator.getPlayer1(), target)){
            MsgUtil.noArgs("%prefix% &a" + target.getName() + " &eSedang melakukan duel dengan orang lain",invitator.getPlayer1(),false);
            return;
        }
        if (target.getName().equalsIgnoreCase(invitator.getPlayer1().getName())){
            MsgUtil.noArgs("%prefix% &cApa maksudmu invite dirimu sendiri??", invitator.getPlayer1(), false);
            return;
        }

        if (!inviteMap.containsKey(invitator)) {
            inviteMap.put(invitator,new HashSet<>());
            inviteMap.get(invitator).add(target.getUniqueId());
        }else if(inviteMap.get(invitator).contains(target.getUniqueId())){
            MsgUtil.noArgs("%prefix% &ePemain dengan nama &r"+ target.getName()+" &esudah kamu invite untuk melakukan duel!!", invitator.getPlayer1(), false);
            return;
        } else inviteMap.get(invitator).add(target.getUniqueId());
        MsgUtil.sendMessage(target,"%prefix% &a" + invitator.getPlayer1().getName() + " &emengundang anda pada duel &c" + kit +" kit");
        MsgUtil.sendMessage(invitator.getPlayer1(),"%prefix% &a" + "&emengundang &a"+ target.getName() +" &epada duel &c" + kit +" kit");
    }

    public static boolean accept(Duel pengundang,Player penerima){
        pengundang.setPlayer2(penerima);
        pengundang.start();
        return true;
    }
}
