package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
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
        PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer());
        String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + prestigeLevel;
        final TagResolver lootResolver = Placeholder.parsed("lootamount", String.valueOf(amount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Loot %s", key, pickaxe.getName(), amount));
        sendEnchantMessage(e.getPlayer(), lootResolver);
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0, 0, 0, 0, "enchantMessage", 0, 0, 0, 0, 0);
        }
    }
}
