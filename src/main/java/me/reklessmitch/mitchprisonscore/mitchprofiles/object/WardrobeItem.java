package me.reklessmitch.mitchprisonscore.mitchprofiles.object;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class WardrobeItem {

    private Material material;
    private String name;
    private List<String> lore;
    private int customData;
    private String command;
    private String permission;

    public WardrobeItem(Material material, String name, List<String> lore, int customData, String command, String permission) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.customData = customData;
        this.command = command;
        this.permission = permission;
    }

    public ItemStack getGuiItem(){
        return ItemCreator.createItem(material, 1, customData, name, lore);
    }
}
