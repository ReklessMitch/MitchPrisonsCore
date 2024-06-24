package mitch.prisonscore.modules.profile.placeholders;

import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class CurrencyPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchcurrency";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ReklessMitch";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        return pp.getPlayerCurrencies().keySet().stream()
                .filter(currency -> params.equalsIgnoreCase(currency.getName()))
                .map(currency -> CurrencyUtils.format(pp.getCurrencyAmount(currency)))
                .findFirst()
                .orElse("Invalid Currency");
    }

}

