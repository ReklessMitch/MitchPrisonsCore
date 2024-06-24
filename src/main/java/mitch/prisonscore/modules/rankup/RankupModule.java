package mitch.prisonscore.modules.rankup;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.rankup.cmds.*;
import mitch.prisonscore.modules.rankup.object.RankupReward;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@EditorName("config")
public class RankupModule extends Module {

    private int startCost = 1000;
    private int costIncrease = 5000;
    private double costMultiplier = 1.5;

    private int maxRanks = 100000;
    private int rewardsEveryXRanks = 5000;
    private Map<Integer, RankupReward> rankupRewards = setUpRankupRewards();

    private Map<Integer, RankupReward> setUpRankupRewards() {
        Map<Integer, RankupReward> rewardsMap = new HashMap<>();
        for (int l = 0; l < maxRanks; l += rewardsEveryXRanks) {
            rewardsMap.put(l, new RankupReward(
                    Material.BEACON,
                    "<green>Rankup Reward",
                    List.of("<green>You have reached rank " + l),
                    List.of("currency give %player% token 1000"),
                    0,
                    l,
                    0
            ));
        }
        return rewardsMap;
    }

    public BigInteger getCost(int rank){
        return BigInteger.valueOf((long) (startCost + (costIncrease * Math.pow(rank, costMultiplier))));
    }

    public static RankupModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.RANKUP);
    }

    public RankupModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.RANKUP;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // cmds
                CmdRankup.class, CmdRankupMax.class,
                CmdRankupGUI.class, CmdRankTop.class, CmdSetRank.class
        );
    }

    @Override
    public void disable() {

    }



}
