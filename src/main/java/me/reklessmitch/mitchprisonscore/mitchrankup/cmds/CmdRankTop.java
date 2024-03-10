package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;


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