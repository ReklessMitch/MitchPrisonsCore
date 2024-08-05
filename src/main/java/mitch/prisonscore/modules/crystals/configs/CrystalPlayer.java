package mitch.prisonscore.modules.crystals.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import mitch.prisonscore.modules.crystals.objects.Crystal;
import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class CrystalPlayer extends SenderEntity<CrystalPlayer> {

    private List<Crystal> crystals = new ArrayList<>();
    private List<Crystal> appliedCrystals = new ArrayList<>();
    private boolean autoTinker = false;
    private int autoTinkerBelow = 50;

    public static CrystalPlayer get(Object oid) {
        return CrystalPlayerColl.get().get(oid);
    }

    @Override
    public CrystalPlayer load(@NotNull CrystalPlayer that) {
        super.load(that);
        return this;
    }


    public void tinker(Crystal crystal) {
        ProfilePlayer profilePlayer = ProfilePlayer.get(getUuid());
        if (profilePlayer == null) return;
        int dust = crystal.getBoostAmount() / 10;
        MessageUtils.sendMessage(getPlayer(), "<green>Successfully tinkered for " + dust + " dust!");
        profilePlayer.addCurrency(Currency.DUST, dust);
        crystals.remove(crystal);
        changed();
    }

    public double getBoostAmount(CrystalType crystal) {
        Optional<Crystal> optionalCrystal =  appliedCrystals.stream().filter(c -> c.getType() == crystal).findFirst();
        return optionalCrystal.map(Crystal::getBoost).orElse(0.0);
    }

    public void applyCrystal(Crystal crystal) {
        if (appliedCrystals.contains(crystal)){
            MessageUtils.sendMessage(getPlayer(), "<red>You already have this crystal applied!");
            return;
        }
        if(appliedCrystals.stream().anyMatch(c -> c.getType() == crystal.getType())) {
            MessageUtils.sendMessage(getPlayer(), "<red>You can only have one crystal of this type applied at a time!");
            return;
        }
        if(appliedCrystals.size() >= 3) {
            MessageUtils.sendMessage(getPlayer(), "<red>You can only have 3 crystals applied at a time!");
            return;
        }
        MessageUtils.sendMessage(getPlayer(), "<green>Successfully applied crystal!");
        PickaxePlayer.get(getUuid()).updatePickaxe();
        appliedCrystals.add(crystal);
        changed();
    }
}
