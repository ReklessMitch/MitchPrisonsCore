package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.modules.pickaxe.gui.TogglesMainGUI;
import org.bukkit.entity.Player;

public class CmdToggles extends PickaxeCommands {

    private static final CmdToggles i = new CmdToggles();
    public static CmdToggles get() { return i; }

    public CmdToggles() {
        this.addAliases("toggles");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        new TogglesMainGUI(player).open();
    }

}
