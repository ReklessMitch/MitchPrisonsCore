package mitch.prisonscore.modules.backpack.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mitch.prisonscore.modules.backpack.config.BackpackPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class BackpackPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchbackpacks";
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
    public String onRequest(OfflinePlayer player, String params) {
        return switch (params) {
            case "backpack_size" -> BackpackPlayer.get(player.getUniqueId()).getCurrentLoad() + "";
            case "backpack_max_size" -> BackpackPlayer.get(player.getUniqueId()).getCapacity() + "";
            default -> "Invalid BP Placeholder";
        };
    }

}