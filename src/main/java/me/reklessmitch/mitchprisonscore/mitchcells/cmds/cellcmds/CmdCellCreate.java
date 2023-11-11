package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

public class CmdCellCreate extends CellCommands {

    public CmdCellCreate(){
        this.addAliases("create");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellConf conf = CellConf.get();
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
