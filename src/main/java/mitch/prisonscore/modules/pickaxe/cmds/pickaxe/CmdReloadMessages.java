package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.utils.MessageUtils;

public class CmdReloadMessages extends PickaxeCommands {

    private static final CmdReloadMessages i = new CmdReloadMessages();
    public static CmdReloadMessages get() { return i; }

    public CmdReloadMessages() {
        this.addAliases("reloadMessages");
    }

    @Override
    public void perform() {
        MessageUtils.cacheTags();
    }

}
