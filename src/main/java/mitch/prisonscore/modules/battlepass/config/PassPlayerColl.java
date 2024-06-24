package mitch.prisonscore.modules.battlepass.config;

import com.massivecraft.massivecore.store.SenderColl;

public class PassPlayerColl extends SenderColl<PassPlayer> {

    private static final PassPlayerColl i = new PassPlayerColl();
    public static PassPlayerColl get() { return i; }
}

