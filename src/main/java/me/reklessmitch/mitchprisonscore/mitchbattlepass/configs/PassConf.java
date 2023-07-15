package me.reklessmitch.mitchprisonscore.mitchbattlepass.configs;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.object.Reward;

import java.util.List;
import java.util.Map;

@Getter
public class PassConf extends Entity<PassConf> {

    public static PassConf i;
    public static PassConf get() { return i; }

    private int creditsToBuyPremium = 5000;
    Map<Integer, List<Reward>> freeRewards;
    Map<Integer, List<Reward>> paidRewards;
    Map<Integer, Long> blocksPerLevel = Map.of(1, 500L, 2, 2500L, 3, 10000L, 4, 50000L);
}