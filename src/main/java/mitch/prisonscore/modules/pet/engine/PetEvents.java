package mitch.prisonscore.modules.pet.engine;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PetEvents extends Engine {

    private static PetEvents i = new PetEvents();
    public static PetEvents get() { return i; }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null && petPlayer.isShowPet()){
            petPlayer.spawnPet(petPlayer.getActivePet());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() == null || petPlayer.getPetEntity() == null || !petPlayer.isShowPet()){
            return;
        }
        petPlayer.despawnPet();
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() == null || petPlayer.getPetEntity() == null || !petPlayer.isShowPet()){
            return;
        }
        final Entity petEntity = petPlayer.getPetEntity();
        final Location playerLocation = event.getPlayer().getLocation();
        if(event.getFrom().getBlock().equals(event.getTo().getBlock())){
            petEntity.setRotation(playerLocation.getYaw(), playerLocation.getPitch());
            return;
        }
        petEntity.teleport(PetUtils.getRelativePetLocation(playerLocation, 0, 1));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() == null || petPlayer.getPetEntity() == null || !petPlayer.isShowPet()){
            return;
        }
        petPlayer.despawnPet();
        petPlayer.spawnPet(petPlayer.getActivePet());
    }

    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() == null || petPlayer.getPetEntity() == null || !petPlayer.isShowPet()){
            return;
        }
        petPlayer.despawnPet();
        petPlayer.spawnPet(petPlayer.getActivePet());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() == null || petPlayer.getPetEntity() == null || !petPlayer.isShowPet()){
            return;
        }
        petPlayer.despawnPet();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerDeathEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(!petPlayer.isShowPet()){
            return;
        }
        petPlayer.spawnPet(petPlayer.getActivePet());
    }

}
