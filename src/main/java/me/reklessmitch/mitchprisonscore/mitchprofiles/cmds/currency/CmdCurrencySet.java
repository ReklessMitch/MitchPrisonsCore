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

public class CmdCurrencySet extends CurrencyCommands {

    public CmdCurrencySet() {
        this.addAliases("set");
        this.setDesc("Set currency of a player");
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
        if(amountInt.equals(BigInteger.valueOf(-1))){
            MessageUtils.sendMessage(sender, "<red>Invalid amount");
            return;
        }
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.set(Currency.valueOf(currency.toUpperCase()), amountInt);
        MessageUtils.sendMessage(player, "<green>You have set <red>" + amountInt + "%s <green>to <red>" + player.getName() + "'s <green>balance");
    }
}
