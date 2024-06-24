package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import org.bukkit.entity.Player;

public class CmdAutoRankup extends PickaxeCommands {

    private static final CmdAutoRankup i = new CmdAutoRankup();
    public static CmdAutoRankup get() { return i; }

    public CmdAutoRankup() {
        this.addAliases("autorankup");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        PickaxePlayer.get(player.getUniqueId()).toggleAutoRankup();
    }

}