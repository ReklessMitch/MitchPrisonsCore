package mitch.prisonscore.modules.booster.cmds.booster;

import mitch.prisonscore.modules.booster.cmds.BoosterCommands;
import mitch.prisonscore.modules.booster.guis.BoosterGUI;
import org.bukkit.entity.Player;

public class CmdBoosterGUI extends BoosterCommands {

    public CmdBoosterGUI() {
        this.addAliases("gui");
    }

    @Override
    public void perform() {
        Player player = me.getPlayer();
        assert player != null;
        new BoosterGUI(player.getUniqueId()).open(player);
    }
}
