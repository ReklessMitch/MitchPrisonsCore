package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;

public class CmdMineGO extends MineCommands {

    private static CmdMineGO i = new CmdMineGO();
    public static CmdMineGO get() { return i; }

    public CmdMineGO(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("go");
    }

    @Override
    public void perform() {
        MinePlayer.get(me.getUniqueId()).teleport();
    }
}
