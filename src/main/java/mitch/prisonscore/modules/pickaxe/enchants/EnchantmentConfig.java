package mitch.prisonscore.modules.pickaxe.enchants;

import com.massivecraft.massivecore.store.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.modules.crystals.configs.CrystalPlayer;
import mitch.prisonscore.modules.crystals.utils.CrystalType;
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
    private CrystalType crystalType;

    public double getProcChance(int currentLevel, UUID playerUUID) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }


    public boolean canProc(int currentLevel, int prestige, UUID playerUUID) {
        CrystalPlayer crystalPlayer = CrystalPlayer.get(playerUUID);
        double crystalBoost = 1 + crystalPlayer.getBoostAmount(crystalType);
        double procChance = getProcChance(currentLevel, playerUUID) + (prestige * procChanceIncreasePerPrestige);
        return Math.random() <= procChance * crystalBoost;
    }

    public void activate() {}

}


