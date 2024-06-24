package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import org.bukkit.Material;

import java.util.List;
import java.util.UUID;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;

public class Jackhammer extends Enchant<Jackhammer.Config> {

    public Jackhammer() {
        super(EnchantType.JACKHAMMER, Jackhammer.Config.class);
    }

    private static double getPetBooster(UUID playerUUID){
        PetPlayer petPlayer = PetPlayer.get(playerUUID);
        return petPlayer.getActivePet() == PetType.JACKHAMMER_BOOST ? petPlayer.getPetBooster(PetType.JACKHAMMER_BOOST) : 0;
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        for(int y = 0; y < prestigeLevel + 1; y++) {
            int blocks = e.getPlayerMine().getBlocksOnYLayer(e.getBlock().getY() - y);
            addTokens(blocks, e.getPlayerMine());
            PickaxePlayer.get(e.getPlayerMine().getUuid()).addBlockBroken(blocks);
        }
        sendEnchantMessage(e.getPlayer());
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0, 0, 0, 0, "enchantMessage", 0, 0, 0, 0, 0);
        }

        @Override
        public double getProcChance(int currentLevel, UUID playerUUID) {
            double petBoost = getPetBooster(playerUUID);
            return super.getProcChance(currentLevel, playerUUID) * (1 + petBoost);
        }
    }
}
