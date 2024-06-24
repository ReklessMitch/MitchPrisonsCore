package mitch.prisonscore.modules.cell.cmds.cellcmds;


import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellLeave extends CellCommands {

    public CmdCellLeave(){
        this.addAliases("leave");
    }

    @Override
    public void perform() {
        CellModule conf = CellModule.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        if(cell == null){
            MessageUtils.sendMessage(me, "<red>You are not in a cell");
            return;
        }
        if(cell.getOwner().equals(me.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>You are the owner of this cell, you must disband it to leave");
            return;
        }
        MessageUtils.sendMessage(me, "<green>You have left your cell");
        cell.removePlayer(me.getUniqueId(), me);
        conf.changed();

    }
}