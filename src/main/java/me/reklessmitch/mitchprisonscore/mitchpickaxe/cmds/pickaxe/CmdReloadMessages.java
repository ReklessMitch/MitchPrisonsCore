package me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.PickaxeCommands;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

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
