package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.rankup.RankupModule;
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
        BigInteger cost = RankupModule.get().getCost(pp.getRank());
        pp.addPercentToNextRankup(Currency.MONEY, percent, cost);
    }
}
