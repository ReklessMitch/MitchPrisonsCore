package mitch.prisonscore.modules.pet;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.xlib.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.colls.PetConfigColl;
import mitch.prisonscore.modules.pet.cmd.CmdPet;
import mitch.prisonscore.modules.pet.engine.PetEvents;
import mitch.prisonscore.modules.pet.entity.PetPlayerColl;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.newpets.PetConfig;
import mitch.prisonscore.modules.pet.placeholders.PetPlaceholders;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pet.util.PetBoost;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Getter
@EditorName("config")
public class PetModule extends Module {

    private transient Map<PetType, Pet> pets = new HashMap<>();
    private boolean advancedPetSystem = false;

    private Map<PetType, PetBoost> petBoosts = Map.of(
            PetType.JACKHAMMER_BOOST, new PetBoost(1.1, 0.1),
            PetType.MONEY, new PetBoost(1.1, 0.1),
            PetType.TOKEN, new PetBoost(1.1, 0.1),
            PetType.CRATE, new PetBoost(1.1, 0.1),
            PetType.SUPPLY_DROP, new PetBoost(1.1, 0.1));

    private Map<PetType, String> petMythicMobs = Map.of(
            PetType.JACKHAMMER_BOOST, "pet_angel",
            PetType.MONEY, "pet_demon",
            PetType.TOKEN, "pet_eagle",
            PetType.CRATE, "pet_fire_dragon",
            PetType.SUPPLY_DROP, "pet_ice_dragon",
            PetType.MINER, "pet_demon",
            PetType.REAPER, "pet_angel");


    private Map<PetType, DisplayItem> petDisplayItems = Map.of(
            PetType.JACKHAMMER_BOOST, new DisplayItem(Material.SKELETON_SKULL,
                    "<green>JACKHAMMER-BOOST PET",
                    List.of("<gray>Increases proc chance of jackhammer",
                            "<gray> ",
                            "<gray>Level: <green>{level}"),
                    0, 0),
            PetType.MONEY, new DisplayItem(Material.CREEPER_HEAD, "<green>MONEY PET",
                    List.of("<gray>Increases money made from backpack",
                            "<gray> ",
                            "<gray>Level: <green>{level}"), 0, 1),
            PetType.TOKEN, new DisplayItem(Material.WITHER_SKELETON_SKULL, "<green>TOKEN PET",
                    List.of("<gray>Increases token amount per block",
                            "<gray> ",
                            "<gray>Level: <green>{level}"), 0, 2),

            PetType.CRATE, new DisplayItem(Material.ZOMBIE_HEAD, "<green>CRATE PET",
                    List.of("<gray>Increases proc chance of key enchantment",
                            "<gray> ",
                            "<gray>Level: <green>{level}"), 0, 3),
            PetType.SUPPLY_DROP, new DisplayItem(Material.CHEST, "<green>SUPPLY DROP PET",
                   List.of("<gray>Increases proc chance of Supply Drop Pet",
                           "<gray> ",
                           "<gray>Level: <green>{level}"), 0, 4));

    private String petHologramFormat = "<green><type> <gray>pet [<level>]";

    public static PetModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.PETS);
    }

    public PetModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.PETS;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                PetPlayerColl.class,

                // events
                PetEvents.class,

                // cmds
                CmdPet.class
        );
        if(advancedPetSystem) {
            setUpPets();
            registerPetConfigs();
        }
        new PetPlaceholders().register();
    }

    @Override
    public void disable() {

    }

    private void setUpPets() {
        Reflections reflections = new Reflections("mitch.prisonscore.modules.pet.newpets");
        Set<Class<? extends Pet>> petClasses = reflections.getSubTypesOf(Pet.class);

        petClasses.forEach(petClass -> {
            try {
                Pet mPet = petClass.getConstructor().newInstance();
                pets.put(mPet.getType(), mPet);

                MitchPrisonsCore.get().log("[Pets] Registered pet: " + mPet.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }

    private void registerPetConfigs() {
        RuntimeTypeAdapterFactory<PetConfig> adapterFactory = RuntimeTypeAdapterFactory.of(
                PetConfig.class, "DO_NOT_CHANGE");

        pets.values().forEach(pet -> adapterFactory.registerSubtype(pet.getConfigClass(), pet.getName()));

        MitchPrisonsCore.get().setGson(MitchPrisonsCore.get().getDummyBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .create()
        );

        MitchPrisonsCore.get().activate(PetConfigColl.class);

        pets.values().forEach(pet -> {
            try {
                PetConfigColl.get().attach((PetConfig) pet.getConfigClass().getDeclaredConstructor().newInstance(), pet.getName());
                System.out.println("Registered enchantment config: " + pet.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Pet configs registered");
    }

    public Pet getPetByType(PetType type) {
        return pets.get(type);
    }


}
