package mitch.prisonscore.modules.mine.cmds.def;

import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.guis.BlockGUI;

public class CmdSetBlock extends MineCommands {

    public CmdSetBlock(){
        this.addAliases("setblock");
    }

    @Override
    public void perform() {
        new BlockGUI(me).open();
    }
}
