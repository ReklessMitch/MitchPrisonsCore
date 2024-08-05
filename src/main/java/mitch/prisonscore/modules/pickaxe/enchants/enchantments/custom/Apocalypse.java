package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;

import java.util.List;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;


public class Apocalypse extends Enchant<Apocalypse.Config> {

    public Apocalypse() {
        super(EnchantType.APOCALYPSE, Apocalypse.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        Mine mine = e.getMine();
        int blocks = mine.apocalypse(e.getPlayer());
        addTokens(blocks, e.getPlayer());
        PickaxePlayer.get(e.getPlayer().getUniqueId()).addBlockBroken(blocks);
        sendEnchantMessage(e.getPlayer());
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    0, 0, 0, 0, "enchantMessage", 0,
                    0, 0, 0, 0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0), CrystalType.APOCALYPSE);
        }
    }
}
