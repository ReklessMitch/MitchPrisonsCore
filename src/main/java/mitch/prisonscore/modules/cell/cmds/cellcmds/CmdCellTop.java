package mitch.prisonscore.modules.cell.cmds.cellcmds;

import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellTop extends CellCommands {

    public CmdCellTop(){
        this.addAliases("celltop");
    }

    @Override
    public void perform() {
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getCellTop());
    }
}
