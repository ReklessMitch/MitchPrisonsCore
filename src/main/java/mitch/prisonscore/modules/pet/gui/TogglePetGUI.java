package mitch.prisonscore.modules.pet.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.xlib.guava.collect.Lists;
import mitch.prisonscore.modules.pet.PetModule;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TogglePetGUI extends ChestGui {

    final Player player;
    PetPlayer petPlayer;

    public TogglePetGUI(Player player) {
        this.player = player;
        this.petPlayer = PetPlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 27, "Pet Toggles"));
        refresh();
        add();
    }

    public void refresh() {
        PetModule.get().getPetDisplayItems().forEach((type, petDisplayItem) -> {
            int slot = petDisplayItem.getSlot();
            ItemStack item = ItemCreator.createItem(petDisplayItem.getMaterial(), 1, 0, petDisplayItem.getItemName(),
                    Lists.newArrayList("Enabled: " + petPlayer.isPetMessageActive(type)));
            getInventory().setItem(slot, item);
            setAction(slot, event -> {
                petPlayer.togglePetMessage(type);
                refresh();
                return true;
            });
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
