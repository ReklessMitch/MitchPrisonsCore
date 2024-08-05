package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.MineType;
import mitch.prisonscore.modules.mine.utils.TypeMine;

public class CmdMineGO extends MineCommands {

    private static CmdMineGO i = new CmdMineGO();
    public static CmdMineGO get() { return i; }

    public CmdMineGO(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addParameter(TypeMine.get(), "mine");
        this.addAliases("go");
    }

    @Override
    public void perform() throws MassiveException {
        MineType type = this.readArg();
        MinePlayer.get(me.getUniqueId()).teleportToMine(type);
    }
}
