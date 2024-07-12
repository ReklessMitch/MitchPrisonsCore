package mitch.prisonscore.utils;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemCreator {

    private static void applyMeta(ItemMeta meta, Integer customModelData, String name, List<String> lore, TagResolver... tagResolvers) {
        if (customModelData != null) {
            meta.setCustomModelData(customModelData);
        }
        if (name != null) {
            meta.displayName(MessageUtils.colorize(name, tagResolvers).decoration(TextDecoration.ITALIC, false));
        }
        if (lore != null) {
            meta.lore(MessageUtils.colorize(lore, tagResolvers));
        }
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
    }

    public static ItemStack createItem(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack createItem(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack createItem(Material material, int amount, int customModelData) {
        return createItem(material, amount, customModelData, null);
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name) {
        return createItem(material, amount, customModelData, name, (List<String>) null);
    }

    public static ItemStack createItem(Material material, int customModelData, String name, List<String> lore, TagResolver... tagResolvers) {
        return createItem(material, 1, customModelData, name, lore, tagResolvers);
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, String... lore) {
        return createItem(material, amount, customModelData, name, Arrays.asList(lore));
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, List<String> lore) {
        return createItem(material, amount, customModelData, name, lore, new TagResolver[0]);
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, List<String> lore, TagResolver... tagResolvers) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        applyMeta(meta, customModelData, name, lore, tagResolvers);
        item.setItemMeta(meta);
        return item;
    }
}
