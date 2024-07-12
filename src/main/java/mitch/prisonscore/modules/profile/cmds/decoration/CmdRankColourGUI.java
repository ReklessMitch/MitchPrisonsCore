package mitch.prisonscore.modules.profile.cmds.decoration;

import mitch.prisonscore.modules.profile.cmds.DecorationCommands;
import mitch.prisonscore.modules.profile.guis.ColourGUI;
import org.bukkit.entity.Player;

public class CmdRankColourGUI extends DecorationCommands {

    private static final CmdRankColourGUI i = new CmdRankColourGUI();
    public static CmdRankColourGUI get() { return i; }

    public CmdRankColourGUI() {
        this.addAliases("rankcolourgui");
    }

    @Override
    public void perform() {
        new ColourGUI((Player) sender, 2).open();
    }

}
