package mitch.prisonscore.modules.crystals.objects;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.modules.crystals.CrystalModule;
import mitch.prisonscore.modules.crystals.utils.CrystalType;
import mitch.prisonscore.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

@SuppressWarnings("FieldMayBeFinal")
@Getter
@Setter
public class Crystal {

    private CrystalType type;
    private int boostAmount;
    private int applyPercent;
    private transient Random random;

    public Crystal(String type, int applyPercent, int boostAmount){
        this.type = CrystalType.valueOf(type);
        this.applyPercent = applyPercent;
        this.boostAmount = boostAmount;
        random = new Random();
    }

    public Crystal(){
        // get a random int between 1 and 100
        random = new Random();
        type = CrystalType.getRandomCrystalType();
        boostAmount = getRandomInt();
        applyPercent = getRandomInt();
    }

    public double getBoost(){
        return boostAmount * 0.01;
    }

    public void addBoostAmount(int amount){
        boostAmount += amount;
    }

    private int getRandomInt(){
        return random.nextInt(100) + 1;
    }

    private int tinker() {
        if (boostAmount < 10 || random.nextDouble() < 0.6) return 0;
        return boostAmount / 10;
    }

    public ItemStack getCrystalItem(){
        ItemStack item = ItemCreator.createItem(Material.EMERALD, 1, 5, "" +
                "<red>" + type.getName() + " CRYSTAL", Lists.newArrayList(
                "<gray>Boost Amount: <green>" + boostAmount,
                "<gray>Apply Percent: <green>" + applyPercent + "%"));
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        final CrystalModule module = CrystalModule.get();
        container.set(module.getTypeKey(), PersistentDataType.STRING, type.toString());
        container.set(module.getPercentKey(), PersistentDataType.INTEGER, applyPercent);
        container.set(module.getBoostKey(), PersistentDataType.INTEGER, boostAmount);
        item.setItemMeta(meta);
        return item;
    }

    public String getString() {
        return type.getName() + ":  " + boostAmount  + "%";
    }
}
