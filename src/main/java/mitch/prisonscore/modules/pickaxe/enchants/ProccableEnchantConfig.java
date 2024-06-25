package mitch.prisonscore.modules.pickaxe.enchants;

import lombok.Getter;

@Getter
public class ProccableEnchantConfig {//extends EnchantmentConfig {

    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private double procChanceIncreasePerPrestige;


//    public ProccableEnchantConfig(DisplayItem displayItem, int levelRequired, int maxLevel, long baseCost,
//                                  long costIncreasePerLevel, String enchantMessage, double priceIncreasePerPrestige,
//                                  int maxPrestige, double baseProcRate, double procRateIncreasePerLevel, double procChanceIncreasePerPrestige) {
//        super(displayItem, levelRequired, maxLevel, baseCost, costIncreasePerLevel, enchantMessage, priceIncreasePerPrestige, maxPrestige);
//        this.baseProcRate = baseProcRate;
//        this.procRateIncreasePerLevel = procRateIncreasePerLevel;
//        this.procChanceIncreasePerPrestige = procChanceIncreasePerPrestige;
//    }

    public double getProcChance(int currentLevel) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }

    public boolean canProc(int currentLevel, int prestige) {
        return Math.random() <= getProcChance(currentLevel) + (prestige * procChanceIncreasePerPrestige);
    }

    public void activate() {}


}
