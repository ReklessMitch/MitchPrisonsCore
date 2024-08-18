package mitch.prisonscore.modules.pickaxe.enchants;

import com.massivecraft.massivecore.store.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.modules.crystals.configs.CrystalPlayer;
import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
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
        return baseProcRate + (currentLevel * procRateIncreasePerLevel) ;
    }

    // @TODO: Add Pet's to this if needed
    public double getLiveProcRate(int currentLevel, int prestige, UUID playerUUID) {
        if(currentLevel == 0) return 0;
        final CrystalPlayer crystalPlayer = CrystalPlayer.get(playerUUID);
        double procChance = getProcChance(currentLevel, playerUUID);
        double crystalBoost = 1 + crystalPlayer.getBoostAmount(crystalType);
        double procChanceIncrease = 1 + (prestige * procChanceIncreasePerPrestige);
        return procChance * procChanceIncrease * crystalBoost * 0.01;
    }


    public boolean canProc(int currentLevel, int prestige, UUID playerUUID) {
        if(currentLevel == 0) return false;
        double fullProcChance = getLiveProcRate(currentLevel, prestige, playerUUID);
        return Math.random() <= fullProcChance;
    }

    public void activate() {}

}


