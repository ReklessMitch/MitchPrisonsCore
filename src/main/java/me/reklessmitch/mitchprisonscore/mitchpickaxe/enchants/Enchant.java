package me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Enchant extends Entity<Enchant> {
    private EnchantType type;
    private DisplayItem displayItem;
    private int levelRequired;
    private int maxLevel;
    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private long baseCost;
    private long costIncreasePerLevel;
    private String enchantMessage;
    private double procChanceIncreasePerPrestige;
    private double priceIncreasePerPrestige;
    private int maxPrestige;

    public Enchant(EnchantType type, Material material, String name, List<String> description, int customModelData, int slot, int levelRequired, int maxLevel, double baseProcRate, double procRateIncreasePerLevel, long baseCost, long costIncreasePerLevel, String enchantMessage) {
        this.type = type;
        this.displayItem = new DisplayItem(material, name, description, customModelData, slot);
        this.levelRequired = levelRequired;
        this.maxLevel = maxLevel;
        this.baseProcRate = baseProcRate;
        this.procRateIncreasePerLevel = procRateIncreasePerLevel;
        this.baseCost = baseCost;
        this.costIncreasePerLevel = costIncreasePerLevel;
        this.enchantMessage = enchantMessage;
        this.procChanceIncreasePerPrestige = 0.1;
        this.priceIncreasePerPrestige = 0.1;
        this.maxPrestige = 1;
    }

    /**
     * @deprecated use {@link #getCost(int, int, int)} instead
     */
    @Deprecated(since = "1.1", forRemoval = true)
    public int getCostOld(int currentLevel, int amountToBuy) {
        int cost = 0;
        for (int i = 0; i < amountToBuy; i++) {
            cost += baseCost + (currentLevel * costIncreasePerLevel);
            currentLevel++;
        }
        return cost;
    }

    public long getCost(int currentLevel, int amountToBuy, int prestige) {
        long firstTerm = baseCost + (currentLevel * costIncreasePerLevel);
        long lastTerm = baseCost + ((currentLevel + amountToBuy - 1) * costIncreasePerLevel);
        long cost = (amountToBuy * (firstTerm + lastTerm)) / 2;
        return (long) (cost * Math.pow(1 + priceIncreasePerPrestige, prestige));
    }

    private int recursiveCost(int currentLevel, long maxBudget, int amount, long cost, int maxLevel, int prestige) {
        if (currentLevel >= maxLevel) {
            return amount;
        }
        cost += getCost(currentLevel, 1, prestige);
        amount++;
        currentLevel++;
        if (cost > maxBudget) {
            return amount - 1;
        }
        return recursiveCost(currentLevel, maxBudget, amount, cost, maxLevel, prestige);
    }


    public final int getMaxAmount(int currentLevel, long maxBudget, int maxLevel, int prestige) {
        return recursiveCost(currentLevel, maxBudget, 0, 0, maxLevel, prestige);
    }

    public double getProcChance(int currentLevel) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }

    public ItemStack getEnchantGuiItem(PPickaxe pickaxe) {
        final TagResolver levelResolver = Placeholder.parsed("level", String.valueOf(pickaxe.getEnchants().get(type)));
        final TagResolver maxLevelResolver = Placeholder.parsed("maxlevel", String.valueOf(maxLevel));
        final TagResolver costResolver = Placeholder.parsed("cost", String.valueOf(getCost(pickaxe.getEnchants().get(type), 1, pickaxe.getEnchantPrestiges().get(type))));
        final TagResolver procChanceResolver = Placeholder.parsed("proc_chance", getProcChance(pickaxe.getEnchants().get(type)) + "%");
        final TagResolver levelRequiredResolver = Placeholder.parsed("level_required", ProfilePlayer.get(pickaxe.getPlayer()).getRank() >= levelRequired
                ? "" : "<grey>Rank Required: <red>" + levelRequired);

        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                displayItem.getItemLore(), levelResolver, maxLevelResolver, costResolver, procChanceResolver, levelRequiredResolver);

    }

    public ItemStack getEnchantGuiToggleItem(PPickaxe pickaxe) {
        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                List.of("<green> ", pickaxe.getEnchantToggle().get(type) ? "<green>Enabled" : "<red>Disabled"));
    }

    public ItemStack getEnchantMessageToggleItem(PPickaxe pickaxe) {
        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                List.of("<green> ", pickaxe.getEnchantMessages().get(type) ? "<green>Enabled" : "<red>Disabled"));
    }
}
