package mitch.prisonscore.modules.cell.cmds.cellcmds;

import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellLeave extends CellCommands {

    public CmdCellLeave(){
        this.addAliases("leave");
    }

    @Override
    public void perform() {
        CellModule conf = CellModule.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        LangConf lang = LangConf.get();
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getNotInACell());
            return;
        }
        if(cell.getOwner().equals(me.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getMustDisbandCellAsOwner());
            return;
        }
        MessageUtils.sendMessage(me, lang.getCellLeftSuccess());
        cell.removePlayer(me.getUniqueId(), me);
        conf.changed();

    }
}