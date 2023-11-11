package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpets.util.PetBoost;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class PetConf extends Entity<PetConf> {
    public static PetConf i;
    public static PetConf get() { return i; }

    private Map<PetType, PetBoost> petBoosts = Map.of(
            PetType.JACKHAMMER_BOOST, new PetBoost(1.1, 0.1),
            PetType.MONEY, new PetBoost(1.1, 0.1),
            PetType.TOKEN, new PetBoost(1.1, 0.1),
            PetType.CRATE, new PetBoost(1.1, 0.1),
            PetType.SUPPLY_DROP, new PetBoost(1.1, 0.1));

    private Map<PetType, String> petMythicMobs = Map.of(
            PetType.JACKHAMMER_BOOST, "Pet_Angel",
            PetType.MONEY, "Pet_Demon",
            PetType.TOKEN, "Pet_Eagle",
            PetType.CRATE, "Pet_Fire_Dragon",
            PetType.SUPPLY_DROP, "Pet_Ice_Dragon");


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
}
