package me.reklessmitch.mitchprisonscore.mitchpets.util;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DisplayItem {

    Material material;
    String itemName;
    List<String> itemLore;
    int customModelData;
    int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
        this.slot = slot;
    }

    public ItemStack getGuiItem(int level) {
        List<String> newLore = new ArrayList<>(itemLore);
        final TagResolver levelResolver = Placeholder.parsed("level", level + "");
        return ItemCreator.createItem(material, 1, customModelData, itemName, newLore, levelResolver);
    }

    public ItemStack getGuiItem() {
        return ItemCreator.createItem(material, 1, customModelData, itemName, itemLore);
    }


}
