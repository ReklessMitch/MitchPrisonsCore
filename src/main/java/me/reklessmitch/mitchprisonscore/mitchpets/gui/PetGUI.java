package me.reklessmitch.mitchprisonscore.mitchpets.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetConf;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
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
        final Map<PetType, DisplayItem> displayItems = PetConf.get().getPetDisplayItems();
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
