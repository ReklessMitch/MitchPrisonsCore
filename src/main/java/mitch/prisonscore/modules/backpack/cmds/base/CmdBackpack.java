package mitch.prisonscore.modules.backpack.cmds.base;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.modules.backpack.cmds.BackpackCommands;
import mitch.prisonscore.modules.backpack.gui.UpgradeBackpackGUI;

public class CmdBackpack extends BackpackCommands {

    private static CmdBackpack i = new CmdBackpack();
    public static CmdBackpack get() { return i; }


    protected CmdBackpackSkins cmdBackpackSkins = new CmdBackpackSkins();

    public CmdBackpack() {
        this.addChild(cmdBackpackSkins);
        this.addAliases("backpack");
    }

    @Override
    public void perform() throws MassiveException {
        new UpgradeBackpackGUI(me).open();
    }
}
