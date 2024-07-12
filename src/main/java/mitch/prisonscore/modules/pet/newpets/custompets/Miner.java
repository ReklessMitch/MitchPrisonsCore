package mitch.prisonscore.modules.pet.newpets.custompets;

import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.newpets.PetConfig;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.utils.configurable.DisplayItem;
import org.bukkit.Material;

import java.util.List;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;


public class Miner extends Pet<Miner.Config> {

    public Miner() {
        super(PetType.MINER, Miner.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent event, int level) {
        MinePlayer minePlayer = event.getPlayerMine();
        int radiusIncrease = level / getConfig().radiusIncreasePerXLevels;
        addTokens(minePlayer.getBeaconsAndBlocksInRegion(
                minePlayer.getBlocksInSquareRaduis(
                        event.getBlock().getLocation().subtract(0, 1, 0),
                        getConfig().defaultRadius + radiusIncrease)), minePlayer);
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
