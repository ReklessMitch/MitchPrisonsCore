package mitch.prisonscore.modules.pickaxe.utils;

public enum EnchantType {
    BEACON("Beacon"),
    FORTUNE("Fortune"),
    GREED("Greed"),
    JACKHAMMER("Jackhammer"),
    KEY_FINDER("KeyFinder"),
    LOOT_FINDER("LootFinder"),
    SPEED("Speed"),
    APOCALYPSE("Apocalypse"),
    SUPPLY_DROP("SupplyDrop"),
    EFFICIENCY("Efficiency"),
    TOKEN_POUCH("TokenPouch"),
    SCAVENGER("Scavenger"),
    BOOST("Boost"),
    HASTE("Haste"),
    NUKE("Nuke"),
    EXPLOSIVE("Explosive");

    private final String displayName;

    EnchantType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
