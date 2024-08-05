package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.utils.configurable.FormatItem;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.List;

public class LootFinder extends Enchant<LootFinder.Config> {

    public LootFinder() {
        super(EnchantType.LOOT_FINDER, LootFinder.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        //final PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer());
        //final String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + prestigeLevel;
        final TagResolver lootResolver = Placeholder.parsed("lootamount", String.valueOf(amount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate give %s Loot %d", e.getPlayer().getName(), amount));
        sendEnchantMessage(e.getPlayer(), lootResolver);
    }


    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    0, 0, 0, 0, "enchantMessage", 0,
                    0, 0, 0, 0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0), CrystalType.LOOTFINDER);
        }
    }
}
