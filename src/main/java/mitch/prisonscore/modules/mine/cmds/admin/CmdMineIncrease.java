package mitch.prisonscore.modules.mine.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

public class CmdMineIncrease extends MineCommands {

    private static CmdMineIncrease i = new CmdMineIncrease();
    public static CmdMineIncrease get() { return i; }


    public CmdMineIncrease() {
        this.addAliases("setsize");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
        this.addParameter(1, TypeInteger.get(), "size");
    }

    @Override
    public void perform() throws MassiveException {
        int amount = this.readArg();
        Player player = (Player) sender;
        MinePlayer playerMine = MinePlayer.get(player.getUniqueId());
        int maxMineSize = MineModule.get().getMaxMineSize();
        int newSize = playerMine.getSize() + amount;
        if(newSize > maxMineSize){
            MessageUtils.sendMessage(sender, "<red>This would exceed max mine size of " + maxMineSize + " setting to max mine size");
            playerMine.upgradeSize(maxMineSize, true);
            return;
        }
        playerMine.upgradeSize(newSize, true);
        MessageUtils.sendMessage(sender, "<green>You have set your mine size to " + newSize);
    }
}
