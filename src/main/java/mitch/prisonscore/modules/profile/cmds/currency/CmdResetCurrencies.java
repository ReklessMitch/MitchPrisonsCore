package mitch.prisonscore.modules.profile.cmds.currency;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import mitch.prisonscore.Perm;
import mitch.prisonscore.modules.profile.cmds.CurrencyCommands;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayerColl;
import mitch.prisonscore.utils.MessageUtils;

public class CmdResetCurrencies extends CurrencyCommands {

    private static CmdResetCurrencies i = new CmdResetCurrencies();
    public static CmdResetCurrencies get() { return i; }

    public CmdResetCurrencies() {
        this.addAliases("resetAllCurrencies");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() {
        ProfilePlayerColl.get().getAll().forEach(ProfilePlayer::resetCurrencies);
        MessageUtils.sendMessage(me, "<green>All player currencies have been reset. (par credits)");
    }
}
