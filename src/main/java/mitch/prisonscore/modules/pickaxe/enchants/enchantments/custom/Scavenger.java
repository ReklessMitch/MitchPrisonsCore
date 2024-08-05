package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Scavenger extends Enchant<Scavenger.Config> {

    public Scavenger() {
        super(EnchantType.SCAVENGER, Scavenger.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer());
        List<EnchantType> addedEnchants = new ArrayList<>();
        pickaxe.addLevelToEnchant(EnchantType.EFFICIENCY, 1);
        pickaxe.addLevelToEnchant(EnchantType.FORTUNE, 1);
        addedEnchants.add(EnchantType.EFFICIENCY);
        addedEnchants.add(EnchantType.FORTUNE);
        if (prestigeLevel == 1) {
            pickaxe.addLevelToEnchant(EnchantType.JACKHAMMER, 1);
            addedEnchants.add(EnchantType.JACKHAMMER);
        } else if (prestigeLevel == 2) {
            pickaxe.addLevelToEnchant(EnchantType.NUKE, 1);
            addedEnchants.add(EnchantType.NUKE);
        }
        pickaxe.changed();
        pickaxe.updatePickaxe();
        sendEnchantMessage(e.getPlayer(), resolveGetter(addedEnchants));
    }

    private TagResolver resolveGetter(List<EnchantType> addedEnchants) {
        return switch (addedEnchants.size()) {
            case 2 -> Placeholder.parsed("enchants", addedEnchants.get(0).getDisplayName() + " & " + addedEnchants.get(1).getDisplayName());
            case 3 -> Placeholder.parsed("enchants", addedEnchants.get(0).getDisplayName() + ", " + addedEnchants.get(1).getDisplayName() + " & " + addedEnchants.get(2).getDisplayName());
            case 4 -> Placeholder.parsed("enchants", addedEnchants.get(0).getDisplayName() + ", " + addedEnchants.get(1).getDisplayName() + ", " + addedEnchants.get(2).getDisplayName() + " & " + addedEnchants.get(3).getDisplayName());
            default -> Placeholder.parsed("enchants", "N/A");
        };
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    0, 0, 0, 0, "enchantMessage", 0
                    , 0, 0, 0, 0,
                    new FormatItem(Material.DIAMOND, "base", List.of("test"), 0), CrystalType.SCAVENGER);
        }
    }
}