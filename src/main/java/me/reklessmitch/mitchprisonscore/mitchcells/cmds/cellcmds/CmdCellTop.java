package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CmdCellTop extends CellCommands {

    public CmdCellTop(){
        this.addAliases("celltop");
    }

    @Override
    public void perform() {
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getCellTop());
    }
}
