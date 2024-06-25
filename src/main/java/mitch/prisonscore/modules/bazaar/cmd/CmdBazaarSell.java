package mitch.prisonscore.modules.bazaar.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import mitch.prisonscore.modules.bazaar.BazaarModule;
import mitch.prisonscore.modules.bazaar.object.ShopValue;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.TypeCurrency;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.math.BigInteger;

public class CmdBazaarSell extends BazaarCommands{

    public CmdBazaarSell(){
        this.addAliases("bazaarsell");
        this.addParameter(TypeCurrency.get(), "currencyToSell");
        this.addParameter(TypeLong.get(), "amount");
        this.addParameter(TypeCurrency.get(), "currencyToSellFor");
        this.addParameter(TypeLong.get(), "price");
    }

    @Override
    public void perform() throws MassiveException {
        Currency currencyToSell = this.readArg();
        long amount = this.readArg();
        Currency currencyToBuy = this.readArg();
        long price = this.readArg();

        final BazaarModule conf = BazaarModule.get();
        ProfilePlayer pp = ProfilePlayer.get(me.getUniqueId());
        if(pp.getCurrencyAmount(currencyToSell).compareTo(BigInteger.valueOf(amount)) < 0){
            final TagResolver currencyResolver = Placeholder.parsed("cts", currencyToSell.getName());
            MessageUtils.sendMessage(me, LangConf.get().getBazaarNotEnoughToSell(), currencyResolver);
            return;
        }
        conf.getSellPrices().get(currencyToSell).get(currencyToBuy).add(new ShopValue(me.getUniqueId(), amount, price));
        conf.changed();
        final TagResolver amountResolver = Placeholder.parsed("amount", "" + amount);
        final TagResolver priceResolver = Placeholder.parsed("price", "" + price);
        final TagResolver currencyResolver = Placeholder.parsed("cts", currencyToSell.getName());
        final TagResolver currencyResolver2 = Placeholder.parsed("ctb", currencyToBuy.getName());

        MessageUtils.sendMessage(me, LangConf.get().getBazaarSellSuccess(), amountResolver, priceResolver, currencyResolver, currencyResolver2);
        pp.take(currencyToSell, amount);
    }
}
