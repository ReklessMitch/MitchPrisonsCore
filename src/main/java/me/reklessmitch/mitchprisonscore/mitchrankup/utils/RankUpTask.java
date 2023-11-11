package me.reklessmitch.mitchprisonscore.mitchrankup.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.Currency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigInteger;

public class RankUpTask extends BukkitRunnable {
    private final Player player;
    private final ProfilePlayer pp;
    private final RankupConf rankupConf;
    private int rank;
    private int ranksIncreased = 0;

    public RankUpTask(Player player, ProfilePlayer pp, RankupConf rankupConf) {
        this.player = player;
        this.pp = pp;
        this.rankupConf = rankupConf;
        this.rank = pp.getRank();
    }

    @Override
    public void run() {
        repeat();
    }

    private void repeat(){
        BigInteger cost = rankupConf.getCost(rank);
        if (pp.getCurrencyAmount(Currency.MONEY).compareTo(cost) >= 0) {
            pp.take(Currency.MONEY, cost);
            rank++;
            ranksIncreased++;
            repeat();
        } else {
            if (ranksIncreased == 0) {
                super.cancel();
            } else {
                pp.setRank(rank);
                pp.changed();
                MessageUtils.sendMessage(player, PlaceholderAPI.setPlaceholders(player, LangConf.get().getRankUp()));
                super.cancel();
            }
        }
    }
}