package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.MessageUtils;

// @TODO Cache this and do it every 5 minutes or so

public class CmdCurencyTop extends CurrencyCommands {

    public CmdCurencyTop(){
        this.addAliases("top");
        this.addParameter(TypeString.get(), "currency");
    }

    @Override
    public void perform() throws MassiveException {
        String currency = this.readArg();
        Currency currencyEnum = Currency.valueOf(currency.toUpperCase());
        MessageUtils.sendMessages(sender, MitchPrisonsCore.get().getLeaderboard().getCurrencyTop().get(currencyEnum));
    }
}
