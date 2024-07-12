package mitch.prisonscore.modules.profile.utils;

import org.bukkit.Material;

public enum Currency {
    TOKEN("Token", "tokens", Material.EMERALD),
    MONEY("Money", "money", Material.GOLD_INGOT),
    CREDIT("Credit", "credits", Material.DIAMOND),
    BEACON("Beacon", "beacons", Material.BEACON),
    DUST("Dust", "dust", Material.GLOWSTONE_DUST);

    private final String name;
    private final String plural;
    private final Material material;

    Currency(String name, String plural, Material material) {
        this.name = name;
        this.plural = plural;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public String getPlural() {
        return plural;
    }

    public Material getMaterial() {
        return material;
    }


}
