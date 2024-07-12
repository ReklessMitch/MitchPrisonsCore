package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
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

public class CmdCurrencyPay extends CurrencyCommands {

    private static CmdCurrencyPay i = new CmdCurrencyPay();
    public static CmdCurrencyPay get() { return i; }

    public CmdCurrencyPay(){
        this.priority = -1; // Essentials Override Test
        this.addAliases("pay");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeCurrency.get(), "currency");
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
        Currency currencyEnum = this.readArg();
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
