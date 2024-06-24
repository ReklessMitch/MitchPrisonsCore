package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.cmds.admin.CmdMineIncrease;
import mitch.prisonscore.modules.mine.cmds.admin.CmdMineResetTest;
import mitch.prisonscore.modules.mine.guis.MineGUI;

public class CmdMine extends MineCommands {

    private static final CmdMine i = new CmdMine();
    public static CmdMine get() { return i; }

    protected CmdMineGO go = new CmdMineGO();
    protected CmdResetMine reset = new CmdResetMine();
    protected CmdSetBlock setBlock = new CmdSetBlock();
    protected CmdMineIncrease increase = new CmdMineIncrease();
    protected CmdMineResetTest resetTest = new CmdMineResetTest();

    public CmdMine(){
        this.addAliases("mine");
        this.addRequirements(RequirementIsPlayer.get());
        this.addChild(go);
        this.addChild(reset);
        this.addChild(setBlock);
        this.addChild(increase);
        this.addChild(resetTest);
    }

    @Override
    public void perform() {
        new MineGUI(me).open();
    }

}
