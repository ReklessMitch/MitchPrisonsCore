package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.utils.MessageUtils;

public class CmdBlockTop extends PickaxeCommands {

    private static final CmdBlockTop i = new CmdBlockTop();
    public static CmdBlockTop get() { return i; }

    public CmdBlockTop() {
        this.addAliases("blocktop");
    }

    @Override
    public void perform() throws MassiveException {
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getBlockTop());
    }
}