package mitch.prisonscore.modules.crystals.engines;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.modules.crates.configs.CratePlayer;
import mitch.prisonscore.modules.crystals.CrystalModule;
import mitch.prisonscore.modules.crystals.configs.CrystalPlayer;
import mitch.prisonscore.modules.crystals.objects.Crystal;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Random;

public class CrystalApply extends Engine {

    private static CrystalApply i = new CrystalApply();
    public static CrystalApply get() { return i;}

    @EventHandler
    public void onCrystalApply(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        ItemMeta itemMeta = event.getItem().getItemMeta();
        if (itemMeta == null) return;
        CrystalModule crystalModule = CrystalModule.get();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        if (pdc.isEmpty()) return;
        NamespacedKey typeKey = crystalModule.getTypeKey();
        NamespacedKey percentKey = crystalModule.getPercentKey();
        NamespacedKey boostKey = crystalModule.getBoostKey();
        if (pdc.has(typeKey, PersistentDataType.STRING) && pdc.has(percentKey, PersistentDataType.INTEGER) && pdc.has(boostKey, PersistentDataType.INTEGER)) {
            String type = pdc.get(typeKey, PersistentDataType.STRING);
            int percent = pdc.get(percentKey, PersistentDataType.INTEGER);
            int boost = pdc.get(boostKey, PersistentDataType.INTEGER);
            addOrTinker(event.getPlayer(), type, percent, boost);
            event.getItem().setAmount(event.getItem().getAmount() - 1);


        }
    }

    private void addOrTinker(Player player, String type, int percent, int boost) {
        CrystalPlayer crystalPlayer = CrystalPlayer.get(player.getUniqueId());
        ProfilePlayer profilePlayer = ProfilePlayer.get(player.getUniqueId());
        if(crystalPlayer.isAutoTinker() && percent < crystalPlayer.getAutoTinkerBelow()){
            MessageUtils.sendMessage(player, "<red>Crystal tinkered! Type: " + type + " Apply Percentage:" + percent + " Boost:" + boost);
            if (Math.random() > 0.6){
                profilePlayer.addCurrency(Currency.DUST, boost / 10);
            }
        }else{
            MessageUtils.sendMessage(player, "<red>Crystal applied! Type: " + type + " Apply Percentage:" + percent + " Boost:" + boost);
            crystalPlayer.getCrystals().add(new Crystal(type, percent, boost));
        }

    }
}
