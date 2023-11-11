package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import org.bukkit.entity.Player;


public class CmdBal extends CurrencyCommands {

    private static final CmdBal i = new CmdBal();
    public static CmdBal get() { return i; }

    public CmdBal(){
        this.addAliases("bal");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        msg("<green><bold>+--- BALANCE ---+");
        msg(PlaceholderAPI.setPlaceholders(player, "<green>| <green> %mitchcurrency_token% Tokens"));
        msg(PlaceholderAPI.setPlaceholders(player, "<green>| <green> %mitchcurrency_money% Money"));
        msg(PlaceholderAPI.setPlaceholders(player,"<green>| <yellow> %mitchcurrency_beacon% Beacons"));
        msg(PlaceholderAPI.setPlaceholders(player,"<green>| <green> %mitchcurrency_credits% Credits"));
    }
}
