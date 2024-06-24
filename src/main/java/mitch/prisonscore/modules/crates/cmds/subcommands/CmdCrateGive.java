package mitch.prisonscore.modules.crates.cmds.subcommands;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import org.bukkit.entity.Player;

public class CmdCrateGive extends MassiveCommand {

    public CmdCrateGive(){
        this.addAliases("give");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "crate");
        this.addParameter(TypeInteger.get(), "amount");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        final Player player = this.readArg();
        final String crateName = this.readArg();
        final int amount = this.readArg();
        CratePlayer.get(player.getUniqueId()).addCrateKey(crateName, amount);
    }
}
