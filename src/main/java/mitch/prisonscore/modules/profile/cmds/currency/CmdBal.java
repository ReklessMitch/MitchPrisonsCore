package mitch.prisonscore.modules.profile.cmds.currency;

import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.profile.utils.CurrencyUtils;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;


public class CmdBal extends CurrencyCommands {

    private static final CmdBal i = new CmdBal();
    public static CmdBal get() { return i; }

    public CmdBal(){
        this.addAliases("bal", "balance");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        ProfilePlayer profilePlayer = ProfilePlayer.get(player);
        final TagResolver beaconResolver = Placeholder.parsed("beacons", profilePlayer.getCurrencyAmount(Currency.BEACON).toString());
        final TagResolver moneyResolver = Placeholder.parsed("money", CurrencyUtils.format(profilePlayer.getCurrencyAmount(Currency.MONEY)));
        final TagResolver tokensResolver = Placeholder.parsed("tokens", CurrencyUtils.format(profilePlayer.getCurrencyAmount(Currency.TOKEN)));
        final TagResolver creditsResolver = Placeholder.parsed("credits", profilePlayer.getCurrencyAmount(Currency.CREDIT).toString());
        final TagResolver dustResolver = Placeholder.parsed("dust", profilePlayer.getCurrencyAmount(Currency.DUST).toString());
        MessageUtils.sendMessages(player, LangConf.get().getGetPlayerBalances(), beaconResolver, moneyResolver, tokensResolver, creditsResolver, dustResolver);

    }
}
