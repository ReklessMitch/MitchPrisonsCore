package mitch.prisonscore.modules.profile.cmds.currency;

import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;

public class CmdCurrencyAdd extends CurrencyCommands {

    private static CmdCurrencyAdd i = new CmdCurrencyAdd();
    public static CmdCurrencyAdd get() { return i; }

    protected CmdCurrencyAddPercent cmdCurrencyAddPercent = new CmdCurrencyAddPercent();
    protected CmdCurrencyAddAmount cmdCurrencyAddAmount = new CmdCurrencyAddAmount();

    public CmdCurrencyAdd() {
        this.addChild(cmdCurrencyAddPercent);
        this.addChild(cmdCurrencyAddAmount);
    }
}
