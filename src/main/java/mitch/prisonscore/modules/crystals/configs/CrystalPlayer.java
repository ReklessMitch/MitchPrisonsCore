package mitch.prisonscore.modules.crystals.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import mitch.prisonscore.modules.crystals.objects.Crystal;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.MessageUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class CrystalPlayer extends SenderEntity<CrystalPlayer> {

    private List<Crystal> crystals = new ArrayList<>();
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
}
