package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import org.bukkit.entity.Player;

public class CmdBlocks extends PickaxeCommands {

    private static final CmdBlocks i = new CmdBlocks();
    public static CmdBlocks get() { return i; }

    public CmdBlocks() {
        this.addAliases("blocks");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        if(player == null) player = me.getPlayer();
        assert player != null;
        PickaxePlayer pick = PickaxePlayer.get(player.getUniqueId());
        msg("<gold><bold>" + player.getName() + "<gold><bold>Block Stats" +
                "\n<gold>| Blocks: <gold>" + pick.getBlocksBroken() +
                "\n<gold>| <gold>Raw Blocks: <gold>" + pick.getRawBlocksBroken());
    }
}
