package mitch.prisonscore.modules.rankup.cmds;

import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import org.bukkit.entity.Player;


public class CmdRankup extends RankupCommands{

    private static final CmdRankup i = new CmdRankup();
    public static CmdRankup get() { return i; }

    public CmdRankup() {
        this.setAliases("rankup", "levelup");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.rankUp();
    }

}
