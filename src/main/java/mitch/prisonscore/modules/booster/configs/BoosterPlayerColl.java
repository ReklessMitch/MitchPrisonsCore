package mitch.prisonscore.modules.booster.configs;


import com.massivecraft.massivecore.store.SenderColl;

public class BoosterPlayerColl extends SenderColl<BoosterPlayer> {

    private static final BoosterPlayerColl i = new BoosterPlayerColl();
    public static BoosterPlayerColl get() { return i; }


}
