package mitch.prisonscore.modules.publicmines.utils;

import org.bukkit.Bukkit;

public enum MineTier {
    NOT_A_TIER(0),
    TIER1(50),
    TIER2(65),
    TIER3(80),
    TIER4(100),
    TIER5(125);

    private final int mineSize;

    MineTier(int mineSize) {
        this.mineSize = mineSize;
    }

    public int getMineSize() {
        return mineSize;
    }

    public boolean isHigherTier(MineTier tier) {
        Bukkit.broadcastMessage(this.ordinal() + " -- " + tier.ordinal());
        return this.ordinal() > tier.ordinal();
    }


}
