package mitch.prisonscore.modules.profile.cmds.misc;

import mitch.prisonscore.modules.profile.cmds.MiscCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.guis.HelpGUI;
import mitch.prisonscore.utils.MessageUtils;

public class CmdHelp extends MiscCommands {

    private static CmdHelp i = new CmdHelp();
    public static CmdHelp get() { return i; }

    public CmdHelp() {
        this.addAliases("help", "menu");
    }

    @Override
    public void perform() {
        new HelpGUI(me).open();
    }

}
