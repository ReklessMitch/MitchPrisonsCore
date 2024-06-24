package mitch.prisonscore.modules.rankup.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.rankup.RankupModule;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigInteger;

public class RankUpTask extends BukkitRunnable {
    private final Player player;
    private final ProfilePlayer pp;
    private final RankupModule rankupConf;
    private int rank;
    private int ranksIncreased = 0;

    public RankUpTask(Player player, ProfilePlayer pp, RankupModule rankupConf) {
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