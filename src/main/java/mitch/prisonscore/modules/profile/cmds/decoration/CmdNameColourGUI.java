package mitch.prisonscore.modules.profile.cmds.decoration;

import mitch.prisonscore.modules.profile.cmds.DecorationCommands;
import mitch.prisonscore.modules.profile.guis.ColourGUI;
import org.bukkit.entity.Player;

public class CmdNameColourGUI extends DecorationCommands {

    private static final CmdNameColourGUI i = new CmdNameColourGUI();
    public static CmdNameColourGUI get() { return i; }

    public CmdNameColourGUI() {
        this.addAliases("namecolourgui");
    }

    @Override
    public void perform() {
        new ColourGUI((Player) sender, 1).open();
    }

}
