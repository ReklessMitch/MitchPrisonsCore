package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class Beacon extends Enchant<Beacon.Config> {

    public Beacon() {
        super(EnchantType.BEACON, Beacon.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        Location centerLocation = e.getBlock().getLocation();
        PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer().getUniqueId());
        int prestigeMulti = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.BEACON);
        int radius = getConfig().defaultRadius + (level / getConfig().radiusIncreasePerXLevels);
        e.getPlayerMine().getBlocksInSquareRaduis(centerLocation, radius + prestigeMulti);
        final Region region =  e.getPlayerMine().getBlocksInSquareRaduis(centerLocation, radius);
        int amountChanged = e.getPlayerMine().replaceBlocksInRegion(region, BlockTypes.BEACON, getConfig().baseChanceToReplace + (level * getConfig().replaceIncreasePerLevel));
        final TagResolver beaconResolver = Placeholder.parsed("blocks", String.valueOf(amountChanged));
        sendEnchantMessage(e.getPlayer(), beaconResolver);
    }

    public static class Config extends EnchantmentConfig {
        int defaultRadius = 3;
        int radiusIncreasePerXLevels = 25;
        double baseChanceToReplace = 0.1;
        double replaceIncreasePerLevel = 0.01;
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0, 0, 0, 0, "enchantMessage", 0, 0, 0, 0, 0);
        }
    }
}
