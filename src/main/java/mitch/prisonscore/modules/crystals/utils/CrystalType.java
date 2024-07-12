package mitch.prisonscore.modules.crystals.utils;

public enum CrystalType {

    JACKHAMMER("<red>JACKHAMMER"),
    APOCALYPSE("<red>APOCALYPSE"),
    KEYFINDER("<red>KEYFINDER"),
    TOKEN_POUCH("<red>TOKEN POUCH"),
    NUKE("<red>NUKE");

    private String name;

    CrystalType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    // get a random crystal type
    public static CrystalType getRandomCrystalType(){
        return values()[(int) (Math.random() * values().length)];
    }


}
