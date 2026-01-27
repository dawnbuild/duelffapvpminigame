package me.dawn.id.pvpMinigame.thegame;

import me.dawn.id.pvpMinigame.MsgUtil;
import me.dawn.id.pvpMinigame.PvpMinigame;
import me.dawn.id.pvpMinigame.PvpMode;
import me.dawn.id.pvpMinigame.area.Area;
import me.dawn.id.pvpMinigame.file.DuelAreaFile;
import me.dawn.id.pvpMinigame.file.KitFile;
import me.dawn.id.pvpMinigame.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Duel {
    Area area;
    Player player1,player2,winner;
    String kitName;
    public static  Map<UUID, OldLocNInv> oldData = new HashMap<>();
    public static Map<Duel, Set<UUID>> inviteMap = new ConcurrentHashMap<>();
    DuelAreaState state;
    public Duel(Area area, Player player1, String kit){
        this.area=area;
        this.player1=player1;
        this.kitName = kit;
        this.state = DuelAreaState.OPEN;
    }
    public DuelAreaState getState(){return state;}
    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}
    public String getKitName(){return kitName;}
    public Area getArea(){return area;}
    public void setPlayer2(Player player2){
        this.player2=player2;
    }

    public void setDuelAreaState(DuelAreaState s){
        this.state=s;
    }
    public void start(){
        inviteMap.get(this).clear();
        System.out.println(this);
        inviteMap.get(this).add(player1.getUniqueId());
        inviteMap.get(this).add(player2.getUniqueId());
        oldData.put(player1.getUniqueId(),new OldLocNInv(player1.getLocation(),player1.getInventory().getArmorContents(),player1.getInventory().getContents()));
        oldData.put(player2.getUniqueId(),new OldLocNInv(player2.getLocation(),player2.getInventory().getArmorContents(),player2.getInventory().getContents()));
        Kit.giveKit(this.getPlayer1(),this.getKitName(), PvpMode.DUEL);
        Kit.giveKit(this.getPlayer2(),this.getKitName(),PvpMode.DUEL);
        this.setDuelAreaState(DuelAreaState.INGAME);
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
        inviteMap.remove(this);
    }
    public void setWinner(Player player){
        this.winner=player;
        //KODE UNTUK PRIZE
        BukkitScheduler bt = Bukkit.getScheduler();
        for (int i =1;i<=5;i++){
            bt.runTaskTimer(PvpMinigame.getInstance(),()->{
                spawnFireworks(winner.getLocation().add(2,0,0));
                spawnFireworks(winner.getLocation().add(0,0,2));
                spawnFireworks(winner.getLocation().add(0,0,-2));
                spawnFireworks(winner.getLocation().add(-2,0,0));
            }, 0,20L).cancel();
        }
        MsgUtil.sendMessage(winner,"%prefix% &eDuel telah berakhir");
        MsgUtil.sendMessage(winner,"%prefix% &eDuel kali ini dimenangkan oleh &a" + winner.getName());
        MsgUtil.sendMessage(winner,"%prefix% &eDengan sisa darah &8[&a" + winner.getHealth() + "&c❤&8]");
    }

    /*TODO: Refactor this code for readable!*/
    public static boolean isPlayersDueling(Player player,Player player2){
        if (getDuelBy(player,player2) != null){
            for (Duel duel : inviteMap.keySet()) {
                if (duel.getState()!=DuelAreaState.INGAME) continue;
                if (inviteMap.get(duel).contains(player.getUniqueId()) && inviteMap.get(duel).contains(player2.getUniqueId())){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isPlayerInDuel(Player player){
        for (Duel duel : inviteMap.keySet()) {
            if (duel.getState()!=DuelAreaState.INGAME) continue;
            if (inviteMap.get(duel).contains(player.getUniqueId()))return true;
        }
        return false;
    }
    public static Duel getDuelBy(Player player,Player player2){
        for (Duel duel : inviteMap.keySet())
//            if (duel.getState()!=DuelAreaState.INGAME) continue;
            if (Duel.inviteMap.get(duel).size() == 2
                    && duel.getState()==DuelAreaState.INGAME
                    && Duel.inviteMap.get(duel).contains(player.getUniqueId())
                    && Duel.inviteMap.get(duel).contains(player2.getUniqueId())) return duel;
        return null;
    }
    public static Duel getInvitation(Player player,Player diundang){
        for(Duel duel : inviteMap.keySet()){
            if (duel.getState()==DuelAreaState.INGAME) continue;
            if (duel.getPlayer1().getUniqueId().equals(player.getUniqueId()) &&
                    inviteMap.get(duel).contains(diundang.getUniqueId())) return duel;
        }
        return null;
    }

    public static Duel getDuelBy(Player player){
        if (!isPlayerInDuel(player)) return null;
        for (Duel duel : inviteMap.keySet())
            if (Duel.inviteMap.get(duel).size() == 2
                    && duel.getState()==DuelAreaState.INGAME
                    && Duel.inviteMap.get(duel).contains(player.getUniqueId())) return duel;

        return null;
    }
    public static Player getOpponent(Player player){
        if (!isPlayerInDuel(player)) return null;
        Player opponent;
        for (Duel d : inviteMap.keySet()){
            if (!inviteMap.get(d).contains(player)) continue;
            inviteMap.get(d).remove(player);
            opponent = Bukkit.getPlayer((UUID) inviteMap.get(d).toArray()[0]);
            inviteMap.get(d).add(player.getUniqueId());
            inviteMap.get(d).add(opponent.getUniqueId());
            return opponent;
        }

        return null;
    }
    /*TODO: ^^^^Refactor this code for readable!^^^^*/

    /* TODO: Triple player safe*/
    public static void invite(Duel invitator,Player target, String kit){
        if (Duel.isPlayerInDuel(target)){
            MsgUtil.noArgs("%prefix% &a" + target.getName() + " &eSedang melakukan duel dengan orang lain",invitator.getPlayer1());
            return;
        }
        if (target.getName().equalsIgnoreCase(invitator.getPlayer1().getName())){
            MsgUtil.noArgs("%prefix% &cApa maksudmu invite dirimu sendiri??", invitator.getPlayer1());
            return;
        }
        if (!KitFile.isKitExist(kit,PvpMode.DUEL)) {
            MsgUtil.noArgs("%prefix% &cKit dengan nama &a" + kit + "&c tidak ditemukan!", invitator.getPlayer1());
            return;
        }
        if (!DuelAreaFile.isAreaExist(invitator.getArea().getName())){
            MsgUtil.noArgs("%prefix% &cArea dengan nama &a" + invitator.getArea().getName() + "&c tidak ditemukan!", invitator.getPlayer1());
            return;
        }
        if (!inviteMap.isEmpty()){
            for(Duel d : inviteMap.keySet()){
                if (d.getPlayer1().getUniqueId().equals(invitator.getPlayer1().getUniqueId())){
                    MsgUtil.noArgs("%prefix% &ePemain dengan nama &r"+ target.getName()+" &esudah kamu invite untuk melakukan duel!!", invitator.getPlayer1(), false);
                    return;
                }
            }
        }
        if (!inviteMap.containsKey(invitator)) {
            inviteMap.put(invitator,new HashSet<>());
            inviteMap.get(invitator).add(target.getUniqueId());
        }else inviteMap.get(invitator).add(target.getUniqueId());
        MsgUtil.sendMessage(target,"%prefix% &a" + invitator.getPlayer1().getName() + " &emengundang anda pada duel &c" + kit +" kit");
        MsgUtil.sendMessage(invitator.getPlayer1(),"%prefix% &a" + "&emengundang &a"+ target.getName() +" &epada duel &c" + kit +" kit");
    }

    public static void accept(Duel pengundang,Player penerima){
        pengundang.setPlayer2(penerima);
        pengundang.start();
    }

    private static void spawnFireworks(Location loc){
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
        Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        fw2.setFireworkMeta(fwm);
    }
}
