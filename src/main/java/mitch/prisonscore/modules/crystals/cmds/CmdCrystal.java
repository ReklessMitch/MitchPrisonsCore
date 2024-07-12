package mitch.prisonscore.modules.crystals.cmds;

import mitch.prisonscore.modules.crystals.cmds.subcmds.CmdCrystalGive;
import mitch.prisonscore.modules.crystals.guis.CrystalGUI;

public class CmdCrystal extends CrystalCommands{

    private static CmdCrystal i = new CmdCrystal();
    public static CmdCrystal get() { return i; }

    protected CmdCrystalGive give = new CmdCrystalGive();

    public CmdCrystal() {
        this.addAliases("crystal", "crystals");
        this.addChild(give);
    }

    @Override
    public void perform() {
        // Open the crystal GUI
        new CrystalGUI(me).open();
    }
}
