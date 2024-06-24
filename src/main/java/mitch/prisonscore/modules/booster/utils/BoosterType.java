package mitch.prisonscore.modules.booster.utils;

import org.bukkit.Material;

public enum BoosterType {
    TOKEN(Material.MAGMA_CREAM, "Token"),
    MONEY(Material.GOLD_INGOT, "Money"),
    BEACON(Material.BEACON, "Beacon");

    private final Material material;

    public Material getMaterial() {
        return material;
    }

    private final String name;

    BoosterType(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
