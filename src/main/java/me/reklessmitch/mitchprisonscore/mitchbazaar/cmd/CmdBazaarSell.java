package me.reklessmitch.mitchprisonscore.mitchbazaar.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;

import java.math.BigInteger;

public class CmdBazaarSell extends BazaarCommands{

    public CmdBazaarSell(){
        this.addAliases("bazaarsell");
        this.addParameter(TypeString.get(), "currencyToSell");
        this.addParameter(TypeLong.get(), "amount");
        this.addParameter(TypeString.get(), "currencyToSellFor");
        this.addParameter(TypeLong.get(), "price");
    }

    @Override
    public void perform() throws MassiveException {
        String cts = this.readArg();
        Currency currencyToSell = Currency.valueOf(cts.toUpperCase());
        long amount = this.readArg();
        String ctb = this.readArg();
        Currency currencyToBuy = Currency.valueOf(ctb.toUpperCase());
        long price = this.readArg();

        BazaarConf conf = BazaarConf.get();
        ProfilePlayer pp = ProfilePlayer.get(me.getUniqueId());
        if(pp.getCurrencyAmount(currencyToSell).compareTo(BigInteger.valueOf(amount)) < 0){
            MessageUtils.sendMessage(me, "<red>You do not have enough " + cts);
            return;
        }
        conf.getSellPrices().get(currencyToSell).get(currencyToBuy).add(new ShopValue(me.getUniqueId(), amount, price));
        conf.changed();
        MessageUtils.sendMessage(me, "<green>You have added " + amount + " " + cts + " to the sell shop for " + price + " " + ctb + "/s");
        pp.take(currencyToSell, amount);
    }
}
