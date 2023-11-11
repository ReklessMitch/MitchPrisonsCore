package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyPay extends CurrencyCommands {

    private static CmdCurrencyPay i = new CmdCurrencyPay();
    public static CmdCurrencyPay get() { return i; }

    public CmdCurrencyPay(){
        this.addAliases("pay");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeString.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        ProfilePlayer receiver = ProfilePlayer.get(player.getUniqueId());
        ProfilePlayer sender = ProfilePlayer.get(me.getUniqueId());
        LangConf conf = LangConf.get();
        if(receiver == null){
            MessageUtils.sendMessages(me, conf.getTransferPlayerNotJoinedServer());
            return;
        }
        if(player.getUniqueId() == me.getUniqueId()){
            MessageUtils.sendMessages(me, conf.getTransferCannotPayYourself());
            return;
        }
        String c = this.readArg();
        Currency currencyEnum = Currency.valueOf(c.toUpperCase());
        if(currencyEnum == null){
            MessageUtils.sendMessage(me, "<red>Currency not found");
            return;
        }

        String amount = this.readArg();
        BigInteger amountInt = CurrencyUtils.parse(amount);
        if(amountInt.longValue() == -1 || amountInt.longValue() <= 0){
            MessageUtils.sendMessages(me, conf.getTransferInvalidAmount());
            return;
        }
        if (sender.getCurrencyAmount(currencyEnum).compareTo(amountInt) < 0) {
            final TagResolver currencyResolver = Placeholder.parsed("currency", currencyEnum.getName());
            MessageUtils.sendMessages(me, conf.getTransferNotEnoughCurrency(), currencyResolver);
        }else{
            sender.take(currencyEnum, amountInt);
            receiver.addCurrency(currencyEnum, amountInt);
            final TagResolver senderResolver = Placeholder.parsed("sender", me.getName());
            final TagResolver receiverResolver = Placeholder.parsed("receiver", player.getName());
            final TagResolver amountResolver = Placeholder.parsed("amount", CurrencyUtils.format(amountInt));
            final TagResolver currencyResolver = Placeholder.parsed("currency", currencyEnum.getName());
            MessageUtils.sendMessages(me, conf.getTransferSuccessSend(), senderResolver, receiverResolver, amountResolver, currencyResolver);
            MessageUtils.sendMessages(player, conf.getTransferSuccessReceive(), senderResolver, receiverResolver, amountResolver, currencyResolver);
        }
    }
}
