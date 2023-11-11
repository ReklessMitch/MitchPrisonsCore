package me.reklessmitch.mitchprisonscore.mitchrankup.object;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class RankupReward {

    private Material material;
    private String itemName;
    private List<String> itemLore;
    private List<String> commands;
    int customModelData;
    int unlockLevel;
    int slot;

    public RankupReward(Material material, String itemName, List<String> itemLore, List<String> commands,
                        int customModelData, int unlockLevel, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.commands = commands;
        this.customModelData = customModelData;
        this.unlockLevel = unlockLevel;
        this.slot = slot;
    }

    public ItemStack getRewardItem() {
        return ItemCreator.createItem(material, 1, customModelData, itemName, itemLore);
    }

    public ItemStack getRewardItem(Material newMaterial) {
        return ItemCreator.createItem(newMaterial, 1, customModelData, itemName, itemLore);
    }
}
