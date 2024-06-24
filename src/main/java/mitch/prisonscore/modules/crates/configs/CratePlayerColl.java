package mitch.prisonscore.modules.crates.configs;

import com.massivecraft.massivecore.store.SenderColl;

public class CratePlayerColl extends SenderColl<CratePlayer> {

    private static final CratePlayerColl i = new CratePlayerColl();
    public static CratePlayerColl get() { return i; }
}
