package mitch.prisonscore.modules.backpack.cmds.base;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.backpack.cmds.BackpackCommands;
import mitch.prisonscore.modules.backpack.config.BackpackPlayer;

public class CmdSell extends BackpackCommands {

    private static final CmdSell i = new CmdSell();
    public static CmdSell get() { return i; }

    public CmdSell() {
        this.addAliases("sell", "SELL");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        BackpackPlayer.get(me.getUniqueId()).sell();
    }
}
