package mitch.prisonscore.modules.pickaxe.enchants.enchantments.custom;

import lombok.Getter;
import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.configurable.FormatItem;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.List;

public class TokenPouch extends Enchant<TokenPouch.Config> {

    public TokenPouch() {
        super(EnchantType.TOKEN_POUCH, TokenPouch.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent e, int level, int prestigeLevel) {
        final ProfilePlayer profilePlayer = ProfilePlayer.get(e.getPlayer());
        final PetPlayer petPlayer = PetPlayer.get(e.getPlayer().getUniqueId());
        final int prestigeMulti = 1 + prestigeLevel;
        final double petMultiplier = petPlayer.getActivePet() == PetType.TOKEN ? petPlayer.getPetBooster(PetType.TOKEN) : 1;
        Booster booster = BoosterPlayer.get(e.getPlayer().getUniqueId()).getActiveTokenBooster();
        final double tokenMultiplier = booster == null ? 1 : booster.getMultiplier();
        final BigInteger tokensToGive = BigInteger.valueOf((long) (this.getConfig().getTokenPouchBaseAmount() +
                ((long) this.getConfig().getTokenPouchIncreasePerLevel() * level) * petMultiplier * prestigeMulti * tokenMultiplier));

        profilePlayer.addCurrency(Currency.TOKEN, tokensToGive);
        final TagResolver tokenResolver = Placeholder.parsed("tokens", String.valueOf(tokensToGive));
        sendEnchantMessage(e.getPlayer(), tokenResolver);
    }

    public static class Config extends EnchantmentConfig {
        @Getter private int tokenPouchBaseAmount = 0;
        @Getter private int tokenPouchIncreasePerLevel = 0;

        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"),
                    0, 0), 0, 0, 0, 0,
                    "enchantMessage", 0, 0, 0, 0,
                    0, new FormatItem(Material.DIAMOND, "base", List.of("test"), 0), CrystalType.TOKEN_POUCH);
        }
    }
}