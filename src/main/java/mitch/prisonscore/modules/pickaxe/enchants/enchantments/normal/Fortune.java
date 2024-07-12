package mitch.prisonscore.modules.pickaxe.enchants.enchantments.normal;

import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;

import java.util.List;

public class Fortune extends Enchant<Fortune.Config> {

    public Fortune() {
        super(EnchantType.FORTUNE, Fortune.Config.class);
    }

    public static class Config extends EnchantmentConfig {

        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"),
                    0, 0), 0, 0, 0, 0,
                    "enchantMessage", 0, 0, 0, 0,
                    0
                    , new FormatItem(Material.DIAMOND, "base", List.of("test"), 0));
        }
    }
}