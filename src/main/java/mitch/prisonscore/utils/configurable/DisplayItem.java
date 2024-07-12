package mitch.prisonscore.utils.configurable;

import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DisplayItem extends FormatItem {

    private int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        super(material, itemName, itemLore, customModelData);
        this.slot = slot;
    }

    public ItemStack getGuiItem(TagResolver... resolvers){
        return ItemCreator.createItem(getMaterial(), 1, getCustomModelData(), getItemName(), getItemLore(), resolvers);
    }

    public ItemStack getPetGuiItem(int level){
        List<String> newLore = new ArrayList<>(getItemLore());
        final TagResolver levelResolver = Placeholder.parsed("level", level + "");
        return ItemCreator.createItem(getMaterial(), 1, getCustomModelData(), getItemName(), newLore, levelResolver);
    }




}
