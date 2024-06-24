package mitch.prisonscore.modules.pickaxe.enchants.enchantments.normal;

import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import org.bukkit.Material;

import java.util.List;

public class Efficiency extends Enchant<Efficiency.Config> {

    public Efficiency() {
        super(EnchantType.EFFICIENCY, Efficiency.Config.class);
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"),
                    0, 0), 0, 0, 0, 0,
                    "enchantMessage", 0, 0, 0, 0, 0
            );
        }
    }
}