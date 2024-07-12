package mitch.prisonscore.modules.crystals.configs;

import com.massivecraft.massivecore.store.SenderColl;

public class CrystalPlayerColl extends SenderColl<CrystalPlayer> {

    private static final CrystalPlayerColl i = new CrystalPlayerColl();
    public static CrystalPlayerColl get() { return i; }


}
