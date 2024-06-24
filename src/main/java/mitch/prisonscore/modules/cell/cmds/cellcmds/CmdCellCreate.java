package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCellCreate extends CellCommands {

    public CmdCellCreate(){
        this.addAliases("create");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellModule conf = CellModule.get();
        if(conf.getCellNames().contains(cellName.toUpperCase())){
            MessageUtils.sendMessage(me, "<red>Cell already exists");
            return;
        }
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>You are already in a cell");
            return;
        }
        conf.getCells().put(cellName.toUpperCase(), new Cell(cellName, me.getUniqueId()));
        MessageUtils.sendMessage(me, "<green>Cell created " + cellName);
        conf.changed();
    }
}
