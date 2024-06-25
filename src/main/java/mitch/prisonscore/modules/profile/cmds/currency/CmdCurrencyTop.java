package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.TypeCurrency;
import mitch.prisonscore.utils.MessageUtils;

public class CmdCurrencyTop extends CurrencyCommands {

    public CmdCurrencyTop(){
        this.addAliases("top");
        this.addParameter(TypeCurrency.get(), "currency");
    }

    @Override
    public void perform() throws MassiveException {
        Currency currency = this.readArg();
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getCurrencyTop().get(currency));
    }
}
