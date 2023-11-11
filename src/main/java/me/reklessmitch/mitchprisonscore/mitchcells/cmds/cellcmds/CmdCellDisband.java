package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;


import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

public class CmdCellDisband extends CellCommands {

    public CmdCellDisband(){
        this.addAliases("disband");
    }

    @Override
    public void perform() {
        CellConf conf = CellConf.get();
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
