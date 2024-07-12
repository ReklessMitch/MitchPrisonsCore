package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import mitch.prisonscore.modules.mine.guis.BlockGUI;

public class CmdSetBlock extends MineCommands {

    public CmdSetBlock(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("setblock");
    }

    @Override
    public void perform() {
        new BlockGUI(me).open();
    }
}
