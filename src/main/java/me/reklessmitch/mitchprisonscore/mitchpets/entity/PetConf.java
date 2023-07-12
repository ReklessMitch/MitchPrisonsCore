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

    Map<PetType, PetBoost> petBoosts = Map.of(
            PetType.JACKHAMMER_BOOST, new PetBoost(1.1, 0.1),
            PetType.MONEY, new PetBoost(1.1, 0.1),
            PetType.TOKEN, new PetBoost(1.1, 0.1),
            PetType.CRATE, new PetBoost(1.1, 0.1),
            PetType.SUPPLY_DROP, new PetBoost(1.1, 0.1));


    Map<PetType, DisplayItem> petDisplayItems = Map.of(
            PetType.JACKHAMMER_BOOST, new DisplayItem(Material.SKELETON_SKULL, "§bJACKHAMMER-BOOST PET",
                    List.of("§7Increases proc chance of jackhammer"), 0),
            PetType.MONEY, new DisplayItem(Material.CREEPER_HEAD, "§bMONEY PET",
                    List.of("§7Increases money made from backpack"), 0),
            PetType.TOKEN, new DisplayItem(Material.WITHER_SKELETON_SKULL, "§bTOKEN PET",
                    List.of("§7Increases token amount per block"), 0),

            PetType.CRATE, new DisplayItem(Material.ZOMBIE_HEAD, "§bCRATE PET",
                    List.of("§7Increases proc chance of key enchantment"), 0),
            PetType.SUPPLY_DROP, new DisplayItem(Material.ZOMBIE_HEAD, "§bSUPPLY DROP PET",
                                           List.of("§7Increases proc chance of Supply Drop Pet"), 0));
}