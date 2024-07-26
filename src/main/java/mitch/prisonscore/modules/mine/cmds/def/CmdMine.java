package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.guis.MineGUI;

public class CmdMine extends MineCommands {

    private static final CmdMine i = new CmdMine();
    public static CmdMine get() { return i; }

    protected CmdMineGO go = new CmdMineGO();
    protected CmdResetMine reset = new CmdResetMine();
    protected CmdSetBlock setBlock = new CmdSetBlock();

    public CmdMine(){
        this.addAliases("mine");
        this.addChild(go);
        this.addChild(reset);
        this.addChild(setBlock);
    }

    @Override
    public void perform() {
        new MineGUI(me).open();
    }

}
