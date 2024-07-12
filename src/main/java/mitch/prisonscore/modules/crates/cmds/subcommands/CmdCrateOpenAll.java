package mitch.prisonscore.modules.crates.cmds.subcommands;

import mitch.prisonscore.modules.crates.cmds.CrateCommands;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import org.bukkit.entity.Player;

public class CmdCrateOpenAll extends CrateCommands {

    public CmdCrateOpenAll() {
        this.addAliases("openall");
    }

    @Override
    public void perform() {
        Player player = me.getPlayer();
        if(player == null) return;
        CratePlayer.get(player.getUniqueId()).openAllCrates();
    }

}
