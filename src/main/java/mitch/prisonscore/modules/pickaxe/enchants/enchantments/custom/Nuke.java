package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;

import java.util.List;

import static mitch.prisonscore.modules.pickaxe.utils.EnchantUtils.addTokens;

public class Nuke extends Enchant<Nuke.Config> {

    public Nuke() {
        super(EnchantType.NUKE, Nuke.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        MinePlayer mine = e.getPlayerMine();
        int blocks = (int) (mine.getVolume() - mine.getVolumeMined());
        addTokens(blocks, mine);
        int beacons = (int) (blocks * 0.02);
        Booster booster = BoosterPlayer.get(e.getPlayer().getUniqueId()).getActiveBeaconBooster();
        if(booster != null) beacons *= booster.getMultiplier();
        ProfilePlayer.get(e.getPlayer().getUniqueId()).addCurrency(Currency.BEACON, beacons);
        PickaxePlayer.get(e.getPlayerMine().getUuid()).addBlockBroken(blocks);
        mine.reset();
        sendEnchantMessage(e.getPlayer());
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0,
                    0, 0, 0, "enchantMessage", 0, 0,
                    0, 0, 0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0));
        }
    }
}

