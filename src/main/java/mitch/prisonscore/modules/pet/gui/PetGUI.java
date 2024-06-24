package mitch.prisonscore.modules.pet.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.PetModule;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

public class PetGUI extends ChestGui {

    private final PetPlayer petPlayer;

    public PetGUI(UUID pID){
        this.petPlayer = PetPlayer.get(pID);
        setInventory(Bukkit.createInventory(null, 18, MessageUtils.colorize(LangConf.get().getPetGuiTitle())));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        PetModule petModule = PetModule.get();

        Map<PetType, DisplayItem> displayItems = petModule.getPetDisplayItems();
        if(petModule.isAdvancedPetSystem()){
            petModule.getPets().forEach((petType, pet) -> displayItems.put(petType, pet.getConfig().getDisplayItem()));
        }
        displayItems.forEach((petType, displayItem) -> {
            int petLevel = petPlayer.getPetLevel(petType);
            getInventory().setItem(displayItem.getSlot(), displayItem.getPetGuiItem(petLevel));
            setAction(displayItem.getSlot(), event -> {
                if(petPlayer.getActivePet() == petType) {
                    event.getWhoClicked().sendMessage("You already have the " + petType.name() + " pet selected.");
                    return true;
                }
                petPlayer.setActivePet(petType);
                event.getWhoClicked().sendMessage("You have selected the " + petType.name() + " pet." + " Level: " + petLevel);
                setUpInventory();
                return true;
            });
        });
    }

    public void open() {
        petPlayer.getPlayer().openInventory(getInventory());
    }
}
