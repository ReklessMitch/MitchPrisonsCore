package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;


import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

public class CmdCellLeave extends CellCommands {

    public CmdCellLeave(){
        this.addAliases("leave");
    }

    @Override
    public void perform() {
        CellConf conf = CellConf.get();
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