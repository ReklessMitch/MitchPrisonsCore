package mitch.prisonscore.modules.crates.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.modules.mine.utils.SerLoc;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class Crate {

    private String name;
    private int size;
    private List<CrateReward> rewards;
    private SerLoc blockLocation;

    public CrateReward getRandomReward() {
        double totalWeight = rewards.stream().mapToDouble(CrateReward::getChance).sum();
        double random = Math.random() * totalWeight;
        for (CrateReward reward : rewards) {
            random -= reward.getChance();
            if (random <= 0) {
                return reward;
            }
        }
        return null;
    }

    public List<CrateReward> getMultipleRewards(int amount) {
        List<CrateReward> massRewards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            massRewards.add(getRandomReward());
        }
        return massRewards;
    }

}
