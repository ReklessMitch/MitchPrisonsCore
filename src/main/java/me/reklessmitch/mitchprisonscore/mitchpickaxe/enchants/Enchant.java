package me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
public class Enchant {
    EnchantType type;
    DisplayItem displayItem;
    int levelRequired;
    int maxLevel;
    double baseProcRate;
    double procRateIncreasePerLevel;
    long baseCost;
    long costIncreasePerLevel;

    public Enchant(EnchantType type, Material material, String name, List<String> description, int customModelData, int slot, int levelRequired, int maxLevel, double baseProcRate, double procRateIncreasePerLevel, long baseCost, long costIncreasePerLevel) {
        this.type = type;
        this.displayItem = new DisplayItem(material, name, description, customModelData, slot);
        this.levelRequired = levelRequired;
        this.maxLevel = maxLevel;
        this.baseProcRate = baseProcRate;
        this.procRateIncreasePerLevel = procRateIncreasePerLevel;
        this.baseCost = baseCost;
        this.costIncreasePerLevel = costIncreasePerLevel;

    }
    public int getCost(int currentLevel, int amountToBuy) {
        int cost = 0;
        for (int i = 0; i < amountToBuy; i++) {
            cost += baseCost + (currentLevel * costIncreasePerLevel);
            currentLevel++;
        }
        return cost;
    }

    public double getProcChance(int currentLevel) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }

    public ItemStack getEnchantGuiItem(PPickaxe pickaxe) {
        ItemStack i = new ItemStack(displayItem.getMaterial());
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(displayItem.getItemName());
        meta.setLore(displayItem.getItemLore().stream().map(s -> {
            if (s == null) return "";
            s = s.replace("{level}", String.valueOf(pickaxe.getEnchants().get(type)));
            s = s.replace("{maxlevel}", String.valueOf(maxLevel));
            s = s.replace("{cost}", String.valueOf(getCost(pickaxe.getEnchants().get(type), 1)));
            return s;
        }).toList());

        meta.setCustomModelData(displayItem.getCustomModelData());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack getEnchantGuiToggleItem(PPickaxe pickaxe) {
        ItemStack i = new ItemStack(displayItem.getMaterial());
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(displayItem.getItemName());
        meta.setLore(List.of("§a ", pickaxe.getEnchantToggle().get(type) ? "§aEnabled" : "§cDisabled"));
        meta.setCustomModelData(displayItem.getCustomModelData());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(meta);
        return i;
    }
}