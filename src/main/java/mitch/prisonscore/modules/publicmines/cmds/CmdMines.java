package mitch.prisonscore.modules.publicmines.cmds;

import mitch.prisonscore.modules.publicmines.guis.MineSelectionGUI;

public class CmdMines extends PublicMineCommands{

    private static final CmdMines i = new CmdMines();
    public static CmdMines get() { return i; }

    public CmdMines() {
        this.addAliases("mines");
    }

    @Override
    public void perform() {
        new MineSelectionGUI(me).open();
    }
}
