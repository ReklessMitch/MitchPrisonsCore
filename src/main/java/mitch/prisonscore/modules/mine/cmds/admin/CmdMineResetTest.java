package mitch.prisonscore.modules.mine.cmds.admin;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;

public class CmdMineResetTest extends MineCommands {

    private static CmdMineResetTest i = new CmdMineResetTest();
    public static CmdMineResetTest get() { return i; }

    public CmdMineResetTest() {
        this.addAliases("resettest");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() {
        MinePlayer.get(me.getUniqueId()).testReset();
    }
}
