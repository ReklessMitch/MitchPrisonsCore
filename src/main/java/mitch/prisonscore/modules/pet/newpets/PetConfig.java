package mitch.prisonscore.modules.pet.newpets;

import com.massivecraft.massivecore.store.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mitch.prisonscore.utils.configurable.DisplayItem;

@AllArgsConstructor
@Getter
public class PetConfig extends Entity<PetConfig> {

    private DisplayItem displayItem;
    private int maxLevel;
    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private String petActivateMessage;

    public boolean canProc(int level) {
        return Math.random() < (baseProcRate + (procRateIncreasePerLevel * level));
    }

}
