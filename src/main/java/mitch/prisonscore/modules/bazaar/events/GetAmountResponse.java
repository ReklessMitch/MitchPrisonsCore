package mitch.prisonscore.modules.bazaar.events;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.bazaar.guis.FinaliseBazaarPayment;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.utils.ChatResponseEvent;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigInteger;

public class GetAmountResponse implements Listener {

    private final Player player;
    @Getter private BigInteger amount = BigInteger.ZERO;
    @Getter private final Currency currencyToBeBrought;
    @Getter private final Currency currencyToBuyWith;
    private final BigInteger maxAmount;

    public GetAmountResponse(Player player, Currency currencyToBeBrought, Currency currencyToBuyWith, BigInteger maxAmount) {
        this.player = player;
        this.currencyToBeBrought = currencyToBeBrought;
        this.currencyToBuyWith = currencyToBuyWith;
        this.maxAmount = maxAmount;
        // @TODO Register this or some shit
        MessageUtils.sendMessage(player, "<green>How much would you like to buy?");
        //MitchPrisonsCore.get().getAwaitingChatResponse().add(player);
    }

    @EventHandler
    public void chatResponseCaught(ChatResponseEvent event){
        Bukkit.broadcastMessage("made it here" + player.getName() + "currency" + currencyToBuyWith.getName());
        if(!event.getPlayer().equals(player)) return;
        try {
            amount = CurrencyUtils.parse(event.getMessage());
        } catch (IllegalArgumentException e) {
            MessageUtils.sendMessage(player, "<red>Invalid amount, (type 'cancel' to cancel)");
            return;
        }
        //core.getAwaitingChatResponse().remove(player);
        if(amount.compareTo(maxAmount) > 0){
            final TagResolver maxAmountResolver = Placeholder.parsed("amount", maxAmount.toString());
            MessageUtils.sendMessage(player, LangConf.get().getBazaarNotEnoughInStock(), maxAmountResolver);
            return;
        }
        Bukkit.getScheduler().runTask(MitchPrisonsCore.get(), () -> {
            FinaliseBazaarPayment gui = new FinaliseBazaarPayment(player, amount, currencyToBeBrought, currencyToBuyWith);
            gui.open();
        });
    }
}
