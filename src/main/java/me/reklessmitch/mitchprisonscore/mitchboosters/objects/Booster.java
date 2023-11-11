package me.reklessmitch.mitchprisonscore.mitchboosters.objects;

import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.TimeFormat;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
@Setter
public class Booster{

    private long timeInSeconds;
    private double multiplier;
    private BoosterType type;
    private boolean active = false;

    public Booster(BoosterType type, double multiplier, long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
        this.multiplier = multiplier;
        this.type = type;
    }

    public ItemStack getBoosterItem(){
        ItemStack item = ItemCreator.createItem(PickaxeConf.get().getBoosterItems().get(type), 1, 0,
                "<green>" + type.name() + " Booster", List.of("<grey>Multiplier: " + multiplier, "<grey>Time: "
                        + TimeFormat.formatSeconds((int) timeInSeconds)));
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(MitchPrisonsCore.get().getTypeKey(), PersistentDataType.STRING, type.toString());
        container.set(MitchPrisonsCore.get().getMultiKey(), PersistentDataType.DOUBLE, multiplier);
        container.set(MitchPrisonsCore.get().getDurationKey(), PersistentDataType.LONG, timeInSeconds);
        item.setItemMeta(meta);
        return item;
    }
}
