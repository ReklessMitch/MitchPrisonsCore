package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.MythicBukkit;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.PPlayerColl;
import me.reklessmitch.mitchprisonscore.mitchpets.util.PetUtils;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

@Getter
public class PetPlayer extends SenderEntity<PetPlayer> {

    public static PetPlayer get(Object oid) {
        return PPlayerColl.get().get(oid);
    }

    private transient Entity petEntity = null;
    @Setter private boolean showPet = true;
    private Map<PetType, Integer> playerPets = initializePets();

    public void despawnPet() {
        if(petEntity != null){
            petEntity.remove();
            petEntity = null;
        }
    }

    @Override
    public PetPlayer load(@NotNull PetPlayer that)
    {
        super.load(that);
        return this;
    }

    private PetType activePet = PetType.TOKEN;

    public void spawnPet(PetType type) {
        if(!showPet) return;
        Location petSpawnLoc = PetUtils.getRelativePetLocation(getPlayer().getLocation(), 0, 1);
        try{
            petEntity = MythicBukkit.inst().getAPIHelper().spawnMythicMob(PetConf.get().getPetMythicMobs().get(type), petSpawnLoc);
            petEntity.setGravity(false);
            petEntity.customName(MessageUtils.colorize("<red>" + getPlayer().getName() + "'s " + type.name() + " Pet"));
            petEntity.setCustomNameVisible(true);
        }catch (InvalidMobTypeException e){
            e.printStackTrace();
        }
    }
    public void setActivePet(PetType type) {
        activePet = type;
        if(petEntity != null){
            petEntity.remove();
        }
        spawnPet(type);
        changed();
    }


    private Map<PetType, Integer> initializePets() {
        Map<PetType, Integer> petsCreate = new EnumMap<>(PetType.class);
        PetType[] petTypes = PetType.values();
        for (PetType petType : petTypes) {
            petsCreate.put(petType, 0);
        }
        return petsCreate;
    }

    public int getPetLevel(PetType type) {
        return playerPets.get(type);
    }

    public void addPetLevel(PetType type, int level) {
        playerPets.replace(type, playerPets.get(type) + level);
        changed();
    }

    public void removePetLevel(PetType type, int level) {
        playerPets.replace(type, playerPets.get(type) - level);
        changed();
    }

    public void setPetLevel(PetType type, int level) {
        playerPets.replace(type, level);
        changed();
    }

    public double getPetBooster(PetType type){
        return Double.parseDouble(MitchPrisonsCore.get().getDecimalFormat()
                .format(PetConf.get().getPetBoosts().get(type).getBoost(getPetLevel(type))));
    }


}
