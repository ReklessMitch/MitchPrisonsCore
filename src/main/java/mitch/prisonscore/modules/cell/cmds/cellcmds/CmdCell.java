package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.guis.CellGUI;

public class CmdCell extends CellCommands {

    private static CmdCell i = new CmdCell();
    public static CmdCell get() { return i; }

    protected CmdAddBeacons addBeacons = new CmdAddBeacons();
    protected CmdCellCreate cellCreate = new CmdCellCreate();
    protected CmdCellInvite cellInvite = new CmdCellInvite();
    protected CmdCellJoin cellJoin = new CmdCellJoin();
    protected CmdCellLeave cellLeave = new CmdCellLeave();
    protected CmdCellDisband cellDisband = new CmdCellDisband();
    protected CmdCellKick cellKick = new CmdCellKick();
    protected CmdCellInfo cellInfo = new CmdCellInfo();
    protected CmdCellTop cmdCellTop = new CmdCellTop();


    public CmdCell(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("cell", "gang", "team");
        this.addChild(addBeacons);
        this.addChild(cellCreate);
        this.addChild(cellInvite);
        this.addChild(cellJoin);
        this.addChild(cellLeave);
        this.addChild(cellDisband);
        this.addChild(cellKick);
        this.addChild(cellInfo);
        this.addChild(cmdCellTop);
    }

    @Override
    public void perform(){
        new CellGUI().open(me);
    }

}
