package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;


import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyRemove extends CurrencyCommands {

    public CmdCurrencyRemove() {
        this.addAliases("remove");
        this.setDesc("Remove currency to a player");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeString.get(), "amount");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        String currency = this.readArg();
        String amount = this.readArg();
        BigInteger amountInt = CurrencyUtils.parse(amount);
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.take(Currency.valueOf(currency.toUpperCase()), amountInt);
        MessageUtils.sendMessage(player, "<green>You have removed <red>" + amount + "%s <green>from <red>" + player.getName() + "'s <green>balance");
    }
}