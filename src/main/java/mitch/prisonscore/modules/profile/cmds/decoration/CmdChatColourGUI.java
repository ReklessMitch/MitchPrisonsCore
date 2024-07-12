package mitch.prisonscore.modules.profile.cmds.decoration;

import mitch.prisonscore.modules.profile.cmds.DecorationCommands;
import mitch.prisonscore.modules.profile.guis.ColourGUI;
import org.bukkit.entity.Player;

public class CmdChatColourGUI extends DecorationCommands {

    private static final CmdChatColourGUI i = new CmdChatColourGUI();
    public static CmdChatColourGUI get() { return i; }

    public CmdChatColourGUI() {
        this.addAliases("chatcolourgui");
    }

    @Override
    public void perform() {
        new ColourGUI((Player) sender, 0).open();
    }

}
