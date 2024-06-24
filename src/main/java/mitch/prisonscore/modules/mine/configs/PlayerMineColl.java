package mitch.prisonscore.modules.mine.configs;

import com.massivecraft.massivecore.store.SenderColl;

public class PlayerMineColl extends SenderColl<MinePlayer> {

    private static final PlayerMineColl i = new PlayerMineColl();
    public static PlayerMineColl get() { return i; }

}
