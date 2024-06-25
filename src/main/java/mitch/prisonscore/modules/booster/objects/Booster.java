package mitch.prisonscore.modules.booster.objects;

import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.modules.booster.BoosterModule;
import mitch.prisonscore.modules.booster.utils.BoosterType;
import mitch.prisonscore.modules.booster.utils.TimeFormat;
import mitch.prisonscore.utils.ItemCreator;
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
        ItemStack item = ItemCreator.createItem(type.getMaterial(), 1, 0,
                "<green>" + type.name() + " Booster", List.of("<grey>Multiplier: " + multiplier, "<grey>Time: "
                        + TimeFormat.formatSeconds((int) timeInSeconds)));
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        final BoosterModule module = BoosterModule.get();
        container.set(module.getTypeKey(), PersistentDataType.STRING, type.toString());
        container.set(module.getMultiKey(), PersistentDataType.DOUBLE, multiplier);
        container.set(module.getDurationKey(), PersistentDataType.LONG, timeInSeconds);
        item.setItemMeta(meta);
        return item;
    }
}
