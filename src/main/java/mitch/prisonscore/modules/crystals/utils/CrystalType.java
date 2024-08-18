package mitch.prisonscore.modules.crystals.utils;

public enum CrystalType {

    JACKHAMMER("<f><redgrad>JACKHAMMER"),
    APOCALYPSE("<f><redgrad>APOCALYPSE"),
    KEYFINDER("<f><redgrad>KEYFINDER"),
    LOOTFINDER("<f><redgrad>LOOTFINDER"),
    TOKEN_POUCH("<f><redgrad>TOKEN POUCH"),
    NUKE("<f><redgrad>NUKE"),
    BEACON("<f><redgrad>BEACON"),
    EXPLOSIVE("<f><redgrad>EXPLOSIVE"),
    SUPPLY_DROP("<f><redgrad>SUPPLY DROP"),
    SCAVENGER("<f><redgrad>SCAVENGER"),
    NONE("<f><redgrad>NONE");


    private String name;

    CrystalType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    // get a random crystal type
    public static CrystalType getRandomCrystalType(){
        return values()[(int) (Math.random() * (values().length - 1))];
    }


}
