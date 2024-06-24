package mitch.prisonscore.modules.crates.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CratePlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "crate";
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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return "" + CratePlayer.get(player.getUniqueId()).getCrateKeys().getOrDefault(params, 0);
    }
}