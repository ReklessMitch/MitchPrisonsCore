package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.mitchrankup.utils.RankUpTask;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ProfilePlayer extends SenderEntity<ProfilePlayer> {
    public static ProfilePlayer get(Object oid) {
        return ProfilePlayerColl.get().get(oid);
    }

    private EnumMap<Currency, BigInteger> playerCurrencies = Arrays.stream(Currency.values())
            .collect(Collectors.toMap(currency -> currency, currency -> BigInteger.ZERO,
                    (u, v) -> u, () -> new EnumMap<>(Currency.class)));

    private List<UUID> friends = new ArrayList<>();
    @Setter private String joinMessage = "";
    private List<Integer> claimedRewards = new ArrayList<>();
    @Setter private int rank = 0;
    @Setter private String currentChatColour = "mpc.chatcolour.red";
    @Setter private String currentNameColour = "mpc.namecolour.red";

    @Override
    public ProfilePlayer load(@NotNull ProfilePlayer that)
    {
        super.load(that);
        return this;
    }

    public void rankUpMax(){
        new RankUpTask(getPlayer(), this, RankupConf.get()).runTaskAsynchronously(MitchPrisonsCore.get());
    }


    public BigInteger getCurrencyAmount(Currency currency){
        return playerCurrencies.get(currency);
    }

    public void addCurrency(Currency currency, BigInteger a){
        playerCurrencies.replace(currency, playerCurrencies.get(currency).add(a));
        MessageUtils.sendMessage(getPlayer(), "(+" + a + " " + getName() + ")");
        if(PPickaxe.get(getPlayer()).isAutoRankup()){
            rankUpMax();
        }
        changed();
    }


    public void addCurrency(Currency currency, long a){
        playerCurrencies.replace(currency, playerCurrencies.get(currency).add(BigInteger.valueOf(a)));
        MessageUtils.sendMessage(getPlayer(), "(+" + a + " " + getName() + ")");
        if(PPickaxe.get(getPlayer()).isAutoRankup()){
            rankUpMax();
        }
        changed();
    }

    public void take(Currency currency, BigInteger a){
        playerCurrencies.replace(currency, playerCurrencies.get(currency).subtract(a));
        changed();
    }

    public void take(Currency currency, long a){
        playerCurrencies.replace(currency, playerCurrencies.get(currency).subtract(BigInteger.valueOf(a)));
        changed();
    }

    public void set(Currency currency, BigInteger a){
        playerCurrencies.replace(currency, a);
        changed();
    }

    public void addPercentToNextRankup(Currency money, int percent, BigInteger cost) {
        final BigInteger amountToAdd = cost.multiply(BigInteger.valueOf(percent)).divide(BigInteger.valueOf(100));
        final TagResolver percentResolver = Placeholder.parsed("percent", String.valueOf(percent));
        final TagResolver costResolver = Placeholder.parsed("cost", cost.toString());
        final TagResolver amountResolver = Placeholder.parsed("amount", amountToAdd.toString());
        MessageUtils.sendMessages(getPlayer(), LangConf.get().getAddPercentOfRankup(), percentResolver, costResolver, amountResolver);
        addCurrency(money, amountToAdd);
        changed();
    }

    public void rankUp() {
        final BigInteger cost = RankupConf.get().getCost(rank);
        BigInteger money = getCurrencyAmount(Currency.MONEY);
        if(money.compareTo(cost) < 0){
            final TagResolver amountNeededResolver = Placeholder.parsed("amount", CurrencyUtils.format(cost.subtract(money)));
            MessageUtils.sendMessages(getPlayer(), LangConf.get().getRankUpNotEnoughMoney(), amountNeededResolver);
            return;
        }
        take(Currency.MONEY, cost);
        setRank(rank + 1);
        final TagResolver rankResolver = Placeholder.parsed("rank", rank + "");
        MessageUtils.sendMessages(getPlayer(), LangConf.get().getRankUpSuccess(), rankResolver);
        changed();
    }
}
