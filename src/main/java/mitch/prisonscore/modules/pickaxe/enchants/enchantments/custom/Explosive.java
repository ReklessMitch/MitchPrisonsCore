package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import org.bukkit.Material;

import java.util.List;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;

@SuppressWarnings("FieldMayBeFinal")
public class Explosive extends Enchant<Explosive.Config> {

    public Explosive() {
        super(EnchantType.EXPLOSIVE, Explosive.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        MinePlayer mine = e.getPlayerMine();
        int radiusIncrease = level / this.getConfig().explosiveLevelsPerIncrease;
        int radius = this.getConfig().explosiveStartRadius + radiusIncrease;
        int blocks = mine.getExplosiveBlocks(e.getBlock().getLocation(), radius);
        addTokens(blocks, mine);
        PickaxePlayer.get(mine.getUuid()).addBlockBroken(blocks);
        sendEnchantMessage(e.getPlayer());
    }

    public static class Config extends EnchantmentConfig {

        private int explosiveStartRadius = 2;
        private int explosiveLevelsPerIncrease = 50;

        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0, 0, 0, 0, "enchantMessage", 0, 0, 0, 0, 0);
        }
    }
}
