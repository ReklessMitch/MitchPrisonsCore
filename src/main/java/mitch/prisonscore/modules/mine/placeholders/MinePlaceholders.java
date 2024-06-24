package mitch.prisonscore.modules.mine.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class MinePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchmines";
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
        MinePlayer minePlayer = MinePlayer.get(player.getUniqueId());
        PickaxePlayer pickaxePlayer = PickaxePlayer.get(player.getUniqueId());
        return switch (params.toLowerCase()){
            case "size" -> "" + minePlayer.getSize();
            case "booster" -> "" + minePlayer.getBooster();
            case "blockstonextrankup" -> "" + MineModule.get().getNextMineLevelBlockRequirement(minePlayer.getSize());
            case "blocksmined" -> "" + pickaxePlayer.getBlocksBroken();
            case "rawblocksmined" -> "" + pickaxePlayer.getRawBlocksBroken();
            case "blocksleftinmine" -> "" + (minePlayer.getVolume() - minePlayer.getVolumeMined());
            case "block" -> "" + minePlayer.getBlock().name();
            default -> "N/A";
        };
    }
}
