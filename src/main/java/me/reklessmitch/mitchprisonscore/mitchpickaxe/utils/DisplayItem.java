package me.reklessmitch.mitchprisonscore.mitchpickaxe.utils;

import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class DisplayItem {

    private Material material;
    private String itemName;
    private List<String> itemLore;
    private int customModelData;
    private int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
        this.slot = slot;
    }

    public ItemStack getGuiItem(int efficiencyLevel){
        ItemStack item = ItemCreator.createItem(material, 1, customModelData, itemName, itemLore);
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, efficiencyLevel);
        return item;
    }


    public ItemStack getGuiItem(){
        return ItemCreator.createItem(material, 1, customModelData, itemName, itemLore);
    }



}
