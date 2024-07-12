package mitch.prisonscore.modules.crates.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import mitch.prisonscore.modules.crates.CratesModule;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CratePlayer extends SenderEntity<CratePlayer> {

    private Map<String, Integer> crateKeys = setUpCrateKey();

    private Map<String, Integer> setUpCrateKey() {
        Map<String, Integer> map = new HashMap<>();
        CratesModule.get().getCrates().forEach(crate -> map.put(crate.getName(), 0));
        return map;
    }

    private boolean reRoll = false;

    public static CratePlayer get(Object oid) {
        return CratePlayerColl.get().get(oid);
    }

    @Override
    public CratePlayer load(@NotNull CratePlayer that)
    {
        super.load(that);
        return this;
    }

    public void addCrateKey(String crateName, int amount) {
        int currentAmount = crateKeys.getOrDefault(crateName, 0);
        crateKeys.put(crateName, currentAmount + amount);
        this.changed();
    }

    public void removeCrateKey(String crateName, int amount) {
        int currentAmount = crateKeys.getOrDefault(crateName, 0);
        crateKeys.put(crateName, currentAmount - amount);
        this.changed();
    }

    public void openAllCrates() {
        crateKeys.forEach((crateName, amount) ->
                CratesModule.get().getCrate(crateName).getMultipleRewards(amount)
                        .forEach(reward -> reward.executeReward(getPlayer(), crateName)));
    }
}
