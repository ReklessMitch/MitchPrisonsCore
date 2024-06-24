package mitch.prisonscore.modules.backpack.config;

import com.massivecraft.massivecore.store.SenderColl;

public class BackPackPlayerColl extends SenderColl<BackpackPlayer> {

    private static final BackPackPlayerColl i = new BackPackPlayerColl();
    public static BackPackPlayerColl get() { return i; }


}
