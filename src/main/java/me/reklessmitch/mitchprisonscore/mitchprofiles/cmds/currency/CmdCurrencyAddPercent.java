package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyAddPercent extends CurrencyCommands {

    private static final CmdCurrencyAddPercent i = new CmdCurrencyAddPercent();
    public static CmdCurrencyAddPercent get() { return i; }


    public CmdCurrencyAddPercent() {
        this.addAliases("percent", "addp", "addpercent");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeInteger.get(), "percentage");
        this.setDesc("Add a percentage to a players balance for the next rankup");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        int percent = this.readArg();
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        BigInteger cost = RankupConf.get().getCost(pp.getRank());
        pp.addPercentToNextRankup(Currency.MONEY, percent, cost);
    }
}
