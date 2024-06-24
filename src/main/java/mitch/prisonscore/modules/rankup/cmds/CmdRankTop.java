package mitch.prisonscore.modules.rankup.cmds;

import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.utils.MessageUtils;


public class CmdRankTop extends RankupCommands {

    private static final CmdRankTop i = new CmdRankTop();
    public static CmdRankTop get() {
        return i;
    }

    public CmdRankTop() {
        this.setAliases("ranktop", "toprank");
    }

    @Override
    public void perform() {
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getRankTop());
    }
}