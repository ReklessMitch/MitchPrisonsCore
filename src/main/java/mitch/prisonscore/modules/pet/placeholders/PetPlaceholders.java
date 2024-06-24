package mitch.prisonscore.modules.pet.placeholders;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PetPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchpets";
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
        if(params.equalsIgnoreCase("activepet")) {
            return PetPlayer.get(player.getUniqueId()).getActivePet().getDisplayName();
        }
        return "Not Active";
    }
}

