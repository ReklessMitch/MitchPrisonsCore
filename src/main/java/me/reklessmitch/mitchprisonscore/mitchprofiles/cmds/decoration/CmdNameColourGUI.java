package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.decoration;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.DecorationCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.NameColourGUI;
import org.bukkit.entity.Player;

public class CmdNameColourGUI extends DecorationCommands {

    private static final CmdNameColourGUI i = new CmdNameColourGUI();
    public static CmdNameColourGUI get() { return i; }

    public CmdNameColourGUI() {
        this.addAliases("namecolourgui");
    }

    @Override
    public void perform() {
        new NameColourGUI().open((Player) sender);
    }

}
