package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
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
import java.util.UUID;

public class KeyFinder extends Enchant<KeyFinder.Config> {

    public KeyFinder() {
        super(EnchantType.KEY_FINDER, KeyFinder.Config.class);
    }

    private static double getPetBooster(UUID playerUUID){
        final PetPlayer petPlayer = PetPlayer.get(playerUUID);
        return petPlayer.getActivePet() == PetType.CRATE ? petPlayer.getPetBooster(PetType.CRATE) : 0;
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        final PickaxePlayer pickaxe = PickaxePlayer.get(e.getPlayer());
        final String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + prestigeLevel;
        final TagResolver keyResolver = Placeholder.parsed("keyamount", String.valueOf(amount));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Mine %s", key, pickaxe.getName(), amount));
        sendEnchantMessage(e.getPlayer(), keyResolver);
    }

    public static class Config extends EnchantmentConfig {
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0), 0, 0, 0, 0, "enchantMessage", 0, 0, 0, 0, 0);
        }

        @Override
        public double getProcChance(int currentLevel, UUID playerUUID) {
            final double petBoost = getPetBooster(playerUUID);
            return super.getProcChance(currentLevel, playerUUID) * (1 + petBoost);
        }
    }
}

