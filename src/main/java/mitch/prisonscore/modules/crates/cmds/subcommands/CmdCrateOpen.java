package mitch.prisonscore.modules.crates.cmds.subcommands;

import mitch.prisonscore.modules.crates.cmds.CrateCommands;
import mitch.prisonscore.modules.crates.guis.CrateOpen;

public class CmdCrateOpen extends CrateCommands {

    public CmdCrateOpen(){
        this.addAliases("open");
    }

    @Override
    public void perform(){
        new CrateOpen(me).open();
    }

}
