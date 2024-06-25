package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.guis.CellKick;

public class CmdCellKick extends CellCommands {

    public CmdCellKick(){
        this.addAliases("kick");
        //this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        new CellKick(me);
    }

}
