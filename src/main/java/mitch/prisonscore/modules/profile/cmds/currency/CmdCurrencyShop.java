package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.guis.StoreCategories;
import org.bukkit.entity.Player;

public class CmdCurrencyShop extends CurrencyCommands {

    private static CmdCurrencyShop i = new CmdCurrencyShop();
    public static CmdCurrencyShop get() { return i; }

    public CmdCurrencyShop() {
        this.addAliases("store", "buy", "shop");
    }

    @Override
    public void perform() throws MassiveException {
        new StoreCategories().open((Player) sender);
    }
}
