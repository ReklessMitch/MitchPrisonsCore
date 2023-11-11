package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.decoration;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.DecorationCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.ChatColourGUI;
import org.bukkit.entity.Player;

public class CmdChatColourGUI extends DecorationCommands {

    private static final CmdChatColourGUI i = new CmdChatColourGUI();
    public static CmdChatColourGUI get() { return i; }

    public CmdChatColourGUI() {
        this.addAliases("chatcolourgui");
    }

    @Override
    public void perform() {
        new ChatColourGUI().open((Player) sender);
    }

}
