package mitch.prisonscore.modules.pickaxe.configs;

import com.massivecraft.massivecore.store.SenderColl;

public class PickaxePlayerColl extends SenderColl<PickaxePlayer> {

    private static final PickaxePlayerColl i = new PickaxePlayerColl();
    public static PickaxePlayerColl get() { return i; }

}
