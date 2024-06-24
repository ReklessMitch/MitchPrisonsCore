package mitch.prisonscore.modules.bazaar.cmd;

import mitch.prisonscore.modules.bazaar.guis.CurrencyGUI;

public class CmdBazaar extends BazaarCommands{

    private static final CmdBazaar i = new CmdBazaar();
    public static CmdBazaar get() { return i; }

    private CmdBazaarSell cmdBazaarSell = new CmdBazaarSell();

    public CmdBazaar(){
        this.addChild(cmdBazaarSell);
        this.addAliases("bazaar", "bz");
    }

    @Override
    public void perform() {
        new CurrencyGUI(me).open();
    }
}
