package mitch.prisonscore.modules.cell.cmds.cellcmds;


import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellDisband extends CellCommands {

    public CmdCellDisband(){
        this.addAliases("disband");
    }

    @Override
    public void perform() {
        CellModule conf = CellModule.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        if(cell == null){
            MessageUtils.sendMessage(me, "<red>You are not in a cell");
            return;
        }
        if(!cell.getOwner().equals(me.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>You are not the owner of this cell");
            return;
        }
        cell.disband();
    }
}
