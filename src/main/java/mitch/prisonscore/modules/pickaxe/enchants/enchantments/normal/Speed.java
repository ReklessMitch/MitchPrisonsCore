package mitch.prisonscore.modules.pickaxe.enchants.enchantments.normal;

import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Speed extends Enchant<Speed.Config> {

    public Speed() {
        super(EnchantType.SPEED, Speed.Config.class);
    }

    private void activate(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.SPEED)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level));
    }

    public static class Config extends EnchantmentConfig {

        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"),
                    0, 0), 0, 0, 0, 0,
                    "enchantMessage", 0, 0, 0, 0,
                    0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0), CrystalType.NONE);
        }
    }
}