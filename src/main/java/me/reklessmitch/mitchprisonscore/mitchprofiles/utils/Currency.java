package me.reklessmitch.mitchprisonscore.mitchprofiles.utils;

import org.bukkit.Material;

public enum Currency {
    TOKEN("Token", "tokens", Material.GOLD_INGOT),
    MONEY("Money", "money", Material.DIAMOND),
    CREDIT("Credit", "credits", Material.BEACON),
    BEACON("Beacon", "beacons", Material.EMERALD);

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
