package mitch.prisonscore.modules.pickaxe.enchants;

import com.massivecraft.massivecore.store.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.utils.configurable.FormatItem;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class EnchantmentConfig extends Entity<EnchantmentConfig> {

    private DisplayItem displayItem;
    private int levelRequired;
    private int maxLevel;
    private long baseCost;
    private long costIncreasePerLevel;
    private String enchantMessage;
    private double priceIncreasePerPrestige;
    private int maxPrestige;
    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private double procChanceIncreasePerPrestige;
    private FormatItem maxedEnchantItem;

    public double getProcChance(int currentLevel, UUID playerUUID) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }


    public boolean canProc(int currentLevel, int prestige, UUID playerUUID) {
        return Math.random() <= getProcChance(currentLevel, playerUUID) + (prestige * procChanceIncreasePerPrestige);
    }

    public void activate() {}

}


