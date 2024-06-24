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

    public static ItemStack createItem(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack createItem(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack createItem(Material material, int amount, int customModelData) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(MessageUtils.colorize(name).decoration(TextDecoration.ITALIC, false));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(MessageUtils.colorize(name).decoration(TextDecoration.ITALIC, false));
        meta.lore(MessageUtils.colorize(Arrays.stream(lore).toList()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(MessageUtils.colorize(name).decoration(TextDecoration.ITALIC, false));
        meta.lore(MessageUtils.colorize(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, int amount, int customModelData, String name, List<String> lore, TagResolver... tagResolvers) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(MessageUtils.colorize(name, tagResolvers).decoration(TextDecoration.ITALIC, false));
        meta.lore(MessageUtils.colorize(lore, tagResolvers));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }


}
