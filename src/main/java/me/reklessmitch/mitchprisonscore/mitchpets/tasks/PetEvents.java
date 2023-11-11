package me.reklessmitch.mitchprisonscore.mitchpets.tasks;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.util.PetUtils;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PetEvents extends Engine {

    private static PetEvents i = new PetEvents();
    public static PetEvents get() { return i; }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null){
            petPlayer.spawnPet(petPlayer.getActivePet());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null && petPlayer.getPetEntity() != null){
            petPlayer.getPetEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null && petPlayer.getPetEntity() != null){
            final Entity petEntity = petPlayer.getPetEntity();
            petEntity.teleport(PetUtils.getRelativePetLocation(event.getPlayer().getLocation(), 0, 1));
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null && petPlayer.getPetEntity() != null){
            final Entity petEntity = petPlayer.getPetEntity();
            petEntity.teleport(PetUtils.getRelativePetLocation(event.getTo(), 0, 1));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null && petPlayer.getPetEntity() != null){
            petPlayer.getPetEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerDeathEvent event){
        final PetPlayer petPlayer = PetPlayer.get(event.getPlayer().getUniqueId());
        if(petPlayer.getActivePet() != null){
            petPlayer.spawnPet(petPlayer.getActivePet());
        }
    }

}
