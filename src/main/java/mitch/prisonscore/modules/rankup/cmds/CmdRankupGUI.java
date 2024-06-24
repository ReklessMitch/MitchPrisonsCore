package mitch.prisonscore.modules.rankup.cmds;

import mitch.prisonscore.modules.rankup.gui.RankupRewardsGUI;
import org.bukkit.entity.Player;

public class CmdRankupGUI extends RankupCommands{

    private static final CmdRankupGUI i = new CmdRankupGUI();
    public static CmdRankupGUI get() { return i; }

    public CmdRankupGUI() {
        this.setAliases("rankupgui", "rgui", "ranks");
        this.setDesc("Rankup GUI");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        new RankupRewardsGUI(player).open();
    }

}
