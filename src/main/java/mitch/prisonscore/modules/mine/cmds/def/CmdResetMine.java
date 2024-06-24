package mitch.prisonscore.modules.mine.cmds.def;

import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import org.bukkit.entity.Player;

public class CmdResetMine extends MineCommands {

    public CmdResetMine(){
        this.addAliases("reset");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        MinePlayer.get(player.getUniqueId()).reset();
    }
}
