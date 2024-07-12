package mitch.prisonscore.modules.mine.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import org.bukkit.entity.Player;

public class CmdMineIncrease extends MineCommands {

    private static CmdMineIncrease i = new CmdMineIncrease();
    public static CmdMineIncrease get() { return i; }


    public CmdMineIncrease() {
        this.addAliases("setsize");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
        this.addParameter(1, TypeInteger.get(), "size");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        int amount = this.readArg();
        MinePlayer playerMine = MinePlayer.get(player.getUniqueId());
        int newSize = playerMine.getSize() + amount;
        playerMine.upgradeSize(newSize, true);
    }
}
