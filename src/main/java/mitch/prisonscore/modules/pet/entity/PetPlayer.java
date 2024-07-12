package mitch.prisonscore.modules.pet.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pet.PetModule;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pet.util.PetUtils;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

@Getter
public class PetPlayer extends SenderEntity<PetPlayer> {

    public static PetPlayer get(Object oid) {
        return PetPlayerColl.get().get(oid);
    }

    private transient Entity petEntity = null;
    @Setter private boolean showPet = true;
    private Map<PetType, Integer> playerPets = initializePets();
    private Map<PetType, Boolean> petMessages = initializePetMessages();

    private Map<PetType, Boolean> initializePetMessages() {
        Map<PetType, Boolean> map = new EnumMap<>(PetType.class);
        for(PetType type : PetType.values()){
            map.put(type, true);
        }
        return map;
    }

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

    private Component getPetHologram(){
        final TagResolver petType = Placeholder.parsed("type", activePet.name());
        final TagResolver petLevel = Placeholder.parsed("level", "" + getPetLevel(activePet));
        return MessageUtils.colorize(PetModule.get().getPetHologramFormat(), petType, petLevel);
    }
    public void spawnPet(PetType type) {
        if(!showPet) return;
        final Location petSpawnLoc = PetUtils.getRelativePetLocation(getPlayer().getLocation(), 0, 1);
        final ArmorStand armorStand = petSpawnLoc.getWorld().spawn(petSpawnLoc, ArmorStand.class);
        final ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(armorStand);
        final ActiveModel activeModel = ModelEngineAPI.createActiveModel(PetModule.get().getPetMythicMobs().get(type));
        modeledEntity.addModel(activeModel, false);
        armorStand.customName(getPetHologram());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        petEntity = armorStand;
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
        PetModule petModule = PetModule.get();
        if(!petModule.isAdvancedPetSystem()){
            for (PetType petType : PetUtils.getNormalPets()) {
                petsCreate.put(petType, 0);
            }
            return petsCreate;
        }
        PetType[] types = PetType.values();
        for (PetType petType : types) {
            petsCreate.put(petType, 0);
        }
        return petsCreate;
    }

    public int getPetLevel(PetType type) {
        return playerPets.get(type);
    }

    private void updatePetHologram(){
        if(petEntity != null && showPet){
            petEntity.customName(getPetHologram());
        }
    }

    public void addPetLevel(PetType type, int level) {
        playerPets.replace(type, playerPets.get(type) + level);
        updatePetHologram();
        changed();
    }

    public void removePetLevel(PetType type, int level) {
        playerPets.replace(type, playerPets.get(type) - level);
        updatePetHologram();
        changed();
    }

    public void setPetLevel(PetType type, int level) {
        playerPets.replace(type, level);
        updatePetHologram();
        changed();
    }

    public double getPetBooster(PetType type){
        return Double.parseDouble(MitchPrisonsCore.get().getDecimalFormat()
                .format(PetModule.get().getPetBoosts().get(type).getBoost(getPetLevel(type))));
    }


    public boolean isPetMessageActive(PetType type) {
        return petMessages.get(type);
    }

    public void togglePetMessage(PetType type) {
        petMessages.replace(type, !petMessages.get(type));
        changed();
    }
}
