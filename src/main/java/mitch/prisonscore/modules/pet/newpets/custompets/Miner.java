package mitch.prisonscore.modules.pet.newpets.custompets;

import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.newpets.PetConfig;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.configurable.DisplayItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;


public class Miner extends Pet<Miner.Config> {

    public Miner() {
        super(PetType.MINER, Miner.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent event, int level) {
        Mine mine = event.getMine();
        Player player = event.getPlayer();
        int radiusIncrease = level / getConfig().radiusIncreasePerXLevels;
        addTokens(mine.getBeaconsAndBlocksInRegion(player,
                mine.getBlocksInSquareRaduis(
                        event.getBlock().getLocation().subtract(0, 1, 0),
                        getConfig().defaultRadius + radiusIncrease)), player);
        sendPetMessage(event.getPlayer());
    }

    public static class Config extends PetConfig {
        int defaultRadius = 3;
        int radiusIncreasePerXLevels = 25;
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    100, 0.1, 0.01, "petActivateMessage");
        }
    }
}
