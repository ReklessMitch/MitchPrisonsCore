package mitch.prisonscore.modules.backpack.cmds.base;

import mitch.prisonscore.modules.backpack.cmds.BackpackCommands;
import mitch.prisonscore.modules.backpack.gui.BackpackSkins;

public class CmdBackpackSkins extends BackpackCommands {

    private static CmdBackpackSkins i = new CmdBackpackSkins();
    public static CmdBackpackSkins get() { return i; }

    public CmdBackpackSkins() {
        this.addAliases("skins");
    }

    @Override
    public void perform() {
        new BackpackSkins(me).open();
    }
}
