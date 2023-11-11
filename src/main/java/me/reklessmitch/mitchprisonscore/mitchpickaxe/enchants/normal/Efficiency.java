package me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.normal;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.Material;

import java.util.List;

public class Efficiency extends Enchant {
    public Efficiency(EnchantType type, Material material, String name, List<String> description, int customModelData, int slot, int levelRequired, int maxLevel, double baseProcRate, double procRateIncreasePerLevel, long baseCost, long costIncreasePerLevel, String enchantMessage) {
        super(type, material, name, description, customModelData, slot, levelRequired, maxLevel, baseProcRate, procRateIncreasePerLevel, baseCost, costIncreasePerLevel, enchantMessage);
    }
}