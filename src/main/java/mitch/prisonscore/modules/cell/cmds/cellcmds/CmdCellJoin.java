package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellJoin extends CellCommands {


    public CmdCellJoin(){
        this.addAliases("join");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        cellName = cellName.toLowerCase();
        CellModule conf = CellModule.get();
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>You are already in a cell");
            return;
        }
        Cell cell = conf.getCellByName(cellName);
        if(cell == null){
            MessageUtils.sendMessage(me, "<red>Cell does not exist");
            return;
        }
        if(cell.getMembers().size() >= conf.getMaxCellSize()){
            MessageUtils.sendMessage(me, "<red>Cell is full");
            return;
        }
        if(cell.getInvites().contains(me.getUniqueId())){
            cell.getMembers().add(me.getUniqueId());
            MessageUtils.sendMessage(me, "<green>Joined cell " + cellName);
            cell.getInvites().remove(me.getUniqueId());
            conf.changed();
        }else{
            MessageUtils.sendMessage(me, "<red>You are not invited to this cell");
        }


    }


}
