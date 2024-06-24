package mitch.prisonscore.modules.crates.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
public class CratePlayer extends SenderEntity<CratePlayer> {

    private Map<String, Integer> crateKeys = Map.of("default", 0);
    private boolean reRoll;

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
}
