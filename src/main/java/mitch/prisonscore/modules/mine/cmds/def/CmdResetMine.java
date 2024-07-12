package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import org.bukkit.entity.Player;

public class CmdResetMine extends MineCommands {

    public CmdResetMine(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("reset");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        MinePlayer.get(player.getUniqueId()).reset();
    }
}
