package me.reklessmitch.mitchprisonscore.mitchpets.entity;

public enum PetType {
    JACKHAMMER_BOOST("Jackhammer Pet"),
    MONEY("Money Pet"),
    TOKEN("Token Pet"),
    CRATE("Crate Pet"),
    SUPPLY_DROP("Supply Drop Pet");

    private final String displayName;

    PetType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
