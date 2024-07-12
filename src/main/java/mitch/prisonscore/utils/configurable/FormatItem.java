package mitch.prisonscore.utils.configurable;

import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.utils.ItemCreator;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("FieldMayBeFinal")
public class FormatItem {

    private Material material;
    private String itemName;
    private List<String> itemLore;
    private int customModelData;

    public FormatItem(Material material, String itemName, List<String> itemLore, int customModelData) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
    }

    public ItemStack getFormatItem(TagResolver... resolvers){
        return ItemCreator.createItem(material, 1, customModelData, itemName, itemLore, resolvers);
    }

}
