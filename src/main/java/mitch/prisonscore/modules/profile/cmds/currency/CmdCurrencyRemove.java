package mitch.prisonscore.modules.profile.cmds.currency;


import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.modules.profile.utils.TypeCurrency;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyRemove extends CurrencyCommands {

    public CmdCurrencyRemove() {
        this.addAliases("remove");
        this.setDesc("Remove currency to a player");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeCurrency.get(), "currency");
        this.addParameter(TypeString.get(), "amount");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        Currency currency = this.readArg();
        String amount = this.readArg();
        BigInteger amountInt = CurrencyUtils.parse(amount);
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.take(currency, amountInt);
        if(senderIsConsole) return;
        // TODO: 25/06/2024
        MessageUtils.sendMessage(player, "<green>You have removed <red>" + amount + "%s <green>from <red>" + player.getName() + "'s <green>balance");
    }
}