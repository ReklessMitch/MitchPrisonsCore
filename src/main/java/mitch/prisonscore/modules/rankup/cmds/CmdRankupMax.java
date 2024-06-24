package mitch.prisonscore.modules.rankup.cmds;

import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import org.bukkit.entity.Player;

public class CmdRankupMax extends RankupCommands {

    private static final CmdRankupMax i = new CmdRankupMax();
    public static CmdRankupMax get() { return i; }

    public CmdRankupMax() {
        this.setAliases("rankupmax", "rmax");
        this.setDesc("Rankup Max");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.rankUpMax();
    }

}
