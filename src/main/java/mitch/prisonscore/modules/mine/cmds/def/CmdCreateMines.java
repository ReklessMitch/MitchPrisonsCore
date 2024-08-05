package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.utils.PrivateMineCreator;

public class CmdCreateMines extends MineCommands {

    private static final CmdCreateMines i = new CmdCreateMines();
    public static CmdCreateMines get() { return i; }

    public CmdCreateMines() {
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
        this.addAliases("createmines");
    }

    @Override
    public void perform() {
        PrivateMineCreator.createMines(150);
    }
}
