package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.colls.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
