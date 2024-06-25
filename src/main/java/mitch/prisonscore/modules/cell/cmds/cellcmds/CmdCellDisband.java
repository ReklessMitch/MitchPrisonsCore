package mitch.prisonscore.modules.cell.cmds.cellcmds;


import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellDisband extends CellCommands {

    public CmdCellDisband(){
        this.addAliases("disband");
    }

    @Override
    public void perform() {
        CellModule conf = CellModule.get();
        LangConf lang = LangConf.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getNotInACell());
            return;
        }
        if(!cell.getOwner().equals(me.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getNotOwnerOfCell());
            return;
        }
        MessageUtils.sendMessage(me, lang.getCellDisbanded());
        cell.disband();
    }
}
