package mitch.prisonscore.modules.pickaxe.enchants;

import lombok.Getter;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.MitchPickaxeModule;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Enchant<T extends EnchantmentConfig> implements Configurable<T> {

    private EnchantType type;

    private Class<T> configClass;

    @Override
    public String getName() {
        return type.name();
    }

    protected Enchant(EnchantType type, Class<T> configClass) {
        this.type = type;
        this.configClass = configClass;
    }

    protected void sendEnchantMessage(Player player, TagResolver... tagResolver){
        if(!PickaxePlayer.get(player).isMessageEnabled(type)) return;
        final String message = this.getConfig().getEnchantMessage();
        if(message == null || message.isEmpty()) return;
        MessageUtils.sendMessage(player, message, tagResolver);
    }

    /**
     * @deprecated use {@link #getCost(int, int, int)} instead
     */
    @Deprecated(since = "1.1", forRemoval = true)
    public int getCostOld(int currentLevel, int amountToBuy) {
        int cost = 0;
        for (int i = 0; i < amountToBuy; i++) {
            cost += this.getConfig().getBaseCost() + (currentLevel * this.getConfig().getCostIncreasePerLevel());
            currentLevel++;
        }
        return cost;
    }

    public long getCost(int currentLevel, int amountToBuy, int prestige) {
        EnchantmentConfig config = this.getConfig();

        long baseCost = config.getBaseCost();
        long costIncreasePerLevel = config.getCostIncreasePerLevel();
        double priceIncreasePerPrestige = config.getPriceIncreasePerPrestige();

        // Calculate first and last terms in the arithmetic series
        long firstTerm = baseCost + (currentLevel * costIncreasePerLevel);
        long lastTerm = baseCost + ((currentLevel + amountToBuy - 1) * costIncreasePerLevel);

        // Calculate the sum of the arithmetic series
        long totalCostWithoutPrestige = (amountToBuy * (firstTerm + lastTerm)) / 2;

        // Apply prestige modifications
        double prestigeMultiplier = 1 + (priceIncreasePerPrestige * prestige);
        double totalCostWithPrestigeIncrease = totalCostWithoutPrestige * prestigeMultiplier;

        // Return the final cost
        return (long) totalCostWithPrestigeIncrease;
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

    public ItemStack getEnchantGuiItem(PickaxePlayer pickaxe) {
        final int maxLevel = this.getConfig().getMaxLevel();
        final int currentLevel = pickaxe.getEnchants().get(type);
        final TagResolver levelResolver = Placeholder.parsed("level", String.valueOf(currentLevel));
        final TagResolver maxLevelResolver = Placeholder.parsed("maxlevel", String.valueOf(maxLevel));
        final TagResolver costResolver = Placeholder.parsed("cost", CurrencyUtils.format(getCost(pickaxe.getEnchants().get(type),
                1, pickaxe.getEnchantPrestiges().get(type))));
        final double procChance = this.getConfig().getProcChance(pickaxe.getEnchants().get(type), pickaxe.getUuid());
        final String procChanceString = MitchPickaxeModule.get().getDf().format(procChance);
        final TagResolver procChanceResolver = Placeholder.parsed("proc_chance", procChanceString + "%");
        final TagResolver levelRequiredResolver = Placeholder.parsed("level_required", ProfilePlayer.get(pickaxe.getPlayer()).getRank() >= this.getConfig().getLevelRequired()
                ? "" : "<grey>Rank Required: <red>" + this.getConfig().getLevelRequired());
        if(currentLevel == maxLevel){
            return this.getConfig().getMaxedEnchantItem().getFormatItem(levelResolver, maxLevelResolver, costResolver, procChanceResolver, levelRequiredResolver);
        }
        DisplayItem displayItem = this.getConfig().getDisplayItem();
        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                displayItem.getItemLore(), levelResolver, maxLevelResolver, costResolver, procChanceResolver, levelRequiredResolver);

    }

    public ItemStack getEnchantGuiToggleItem(PickaxePlayer pickaxe) {
        DisplayItem displayItem = this.getConfig().getDisplayItem();
        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                List.of("<green> ", pickaxe.getEnchantToggle().get(type) ? "<green>Enabled" : "<red>Disabled"));
    }

    public ItemStack getEnchantMessageToggleItem(PickaxePlayer pickaxe) {
        DisplayItem displayItem = this.getConfig().getDisplayItem();
        return ItemCreator.createItem(displayItem.getMaterial(), 1, displayItem.getCustomModelData(), displayItem.getItemName(),
                List.of("<green> ", pickaxe.getEnchantMessages().get(type) ? "<green>Enabled" : "<red>Disabled"));
    }

    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {}
}
