package mitch.prisonscore.modules.profile.cmds.currency;

import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;

public class CmdCurrency extends CurrencyCommands {

    private static CmdCurrency i = new CmdCurrency();
    public static CmdCurrency get() {return i;}

    protected CmdCurrencyAdd cmdCurrencyAdd = new CmdCurrencyAdd();
    protected CmdCurrencyPay cmdCurrencyPay = new CmdCurrencyPay();
    protected CmdCurrencyRemove cmdCurrencyRemove = new CmdCurrencyRemove();
    protected CmdCurrencySet cmdCurrencySet = new CmdCurrencySet();
    protected CmdCurrencyShop cmdCurrencyShop = new CmdCurrencyShop();
    protected CmdCurrencyTop cmdCurencyTop = new CmdCurrencyTop();

    public CmdCurrency() {
        this.addAliases("currency");
        this.addChild(cmdCurrencyShop);
        this.addChild(cmdCurencyTop);
        this.addChild(cmdCurrencyAdd);
        this.addChild(cmdCurrencyPay);
        this.addChild(cmdCurrencyRemove);
        this.addChild(cmdCurrencySet);
    }
}
