package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.MythicBukkit;
import lombok.Getter;
import lombok.Setter;
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

    private Map<PetType, Pet> pets = initializePets();

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
    }


    private Map<PetType, Pet> initializePets() {
        Map<PetType, Pet> petsCreate = new EnumMap<>(PetType.class);
        petsCreate.put(PetType.CRATE, new Pet(PetType.CRATE));
        petsCreate.put(PetType.MONEY, new Pet(PetType.MONEY));
        petsCreate.put(PetType.SUPPLY_DROP, new Pet(PetType.SUPPLY_DROP));
        petsCreate.put(PetType.JACKHAMMER_BOOST, new Pet(PetType.JACKHAMMER_BOOST));
        petsCreate.put(PetType.TOKEN, new Pet(PetType.TOKEN));
        return petsCreate;
    }

    public Pet getPet(PetType type) {
        return pets.getOrDefault(type, null);
    }


}
