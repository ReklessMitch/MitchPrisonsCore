package mitch.prisonscore.modules.pet.util;

import java.util.ArrayList;
import java.util.List;

public enum PetType {
    JACKHAMMER_BOOST("Jackhammer Pet"),
    MONEY("Money Pet"),
    TOKEN("Token Pet"),
    CRATE("Crate Pet"),
    SUPPLY_DROP("Supply Drop Pet"),
    MINER("Miner Pet"),
    REAPER("Reaper Pet");

    private final String displayName;

    PetType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getPetTypes() {
        List<String> petTypes = new ArrayList<>();
        for (PetType petType : PetType.values()) {
            petTypes.add(petType.name());
        }
        return petTypes;
    }
}
