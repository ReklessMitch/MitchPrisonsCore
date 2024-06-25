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
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyAddAmount extends CurrencyCommands {

    private static CmdCurrencyAddAmount i = new CmdCurrencyAddAmount();
    public static CmdCurrencyAddAmount get() { return i; }

    public CmdCurrencyAddAmount() {
        this.addAliases("addc", "addcurrency", "addamount");
        this.setDesc("Add currency to a player");
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
        BigInteger amountLong = CurrencyUtils.parse(amount);
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.addCurrency(currency, amountLong);
        if(senderIsConsole) return;

        TagResolver playerResolver = Placeholder.parsed("player", player.getName());
        TagResolver currencyResolver = Placeholder.parsed("currency", currency.getName());
        TagResolver amountResolver = Placeholder.parsed("amount", amount);

        MessageUtils.sendMessage(me, LangConf.get().getAddedCurrencyToPlayer(), playerResolver, currencyResolver, amountResolver);

    }
}
