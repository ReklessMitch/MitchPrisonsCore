package me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants;

import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class CustomEnchant extends DefaultEnchant{

    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private double procChanceIncreasePerPrestige;
    private double priceIncreasePerPrestige;
    private int maxPrestige;
    
    public CustomEnchant(EnchantType type, Material material, String name, List<String> description, int customModelData, 
                         int slot, int levelRequired, int maxLevel, long baseCost, long costIncreasePerLevel) {
        super(type, material, name, description, customModelData, slot, levelRequired, maxLevel, baseCost, costIncreasePerLevel);
        baseProcRate = 0.0;
        procRateIncreasePerLevel = 0.0;
        procChanceIncreasePerPrestige = 0.0;
        priceIncreasePerPrestige = 0.0;
        maxPrestige = 0;
    }
    
    public CustomEnchant(EnchantType type, Material material, String name, List<String> description, int customModelData, 
                         int slot, int levelRequired, int maxLevel, long baseCost, long costIncreasePerLevel, double baseProcRate, 
                         double procChanceIncreasePerPrestige, double priceIncreasePerPrestige, int maxPrestige) {
        super(type, material, name, description, customModelData, slot, levelRequired, maxLevel, baseCost, costIncreasePerLevel);
        this.baseProcRate = baseProcRate;
        this.procChanceIncreasePerPrestige = procChanceIncreasePerPrestige;
        this.priceIncreasePerPrestige = priceIncreasePerPrestige;
        this.maxPrestige = maxPrestige;
    }
    
    @Override
    public long getCost(int currentLevel, int amountToBuy, int prestige) {
        long firstTerm = getBaseCost() + (currentLevel * getCostIncreasePerLevel());
        long lastTerm = getBaseCost() + ((currentLevel + amountToBuy - 1) * getCostIncreasePerLevel());
        long cost = (amountToBuy * (firstTerm + lastTerm)) / 2;
        return (long) (cost * Math.pow(1 + priceIncreasePerPrestige, prestige));
    }

    public double getProcChance(int currentLevel) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel) * (1 + procChanceIncreasePerPrestige);
    }
    
    @Override
    public ItemStack getEnchantGuiItem(PPickaxe pickaxe) {
        DisplayItem displayItem = getDisplayItem();
        int enchantLevel = pickaxe.getEnchantLevel(getType());
        final TagResolver levelResolver = Placeholder.parsed("level", String.valueOf(enchantLevel));
        final TagResolver maxLevelResolver = Placeholder.parsed("maxlevel", String.valueOf(getMaxLevel()));
        final TagResolver costResolver = Placeholder.parsed("cost", String.valueOf(getCost(enchantLevel, 1, pickaxe.getEnchantPrestiges().get(getType()))));
        final TagResolver procChanceResolver = Placeholder.parsed("proc_chance", getProcChance(enchantLevel) + "%");
        final TagResolver levelRequiredResolver = Placeholder.parsed("levelRequired", ProfilePlayer.get(pickaxe.getPlayer()).getRank() >= getLevelRequired()
                ? "" : "<grey>Rank Required: <red>" + getLevelRequired());

        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                displayItem.getItemLore(), levelResolver, maxLevelResolver, costResolver, levelRequiredResolver, procChanceResolver);

    }
}
