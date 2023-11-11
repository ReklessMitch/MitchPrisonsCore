package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdRankup extends RankupCommands{

    private static final CmdRankup i = new CmdRankup();
    public static CmdRankup get() { return i; }

    public CmdRankup() {
        this.setAliases("rankup", "levelup");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.rankUp();
    }

}
