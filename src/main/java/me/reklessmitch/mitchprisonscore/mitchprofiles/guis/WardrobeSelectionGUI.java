package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.WardrobeItem;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class WardrobeSelectionGUI extends ChestGui {

    private final Player player;

    public WardrobeSelectionGUI(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(ProfilesConf.get().getWardrobeTitle())));
        init();
        add();
    }

    private void init() {
        int[] backpackSlots = {0, 1, 2, 9, 10, 11, 18, 19, 20};
        int[] wingSlots = {3, 4, 5, 12, 13, 14, 21, 22, 23};
        int[] armourSlots = {6, 7, 8, 15, 16, 17, 24, 25, 26};
        DisplayItem backpackItem = ProfilesConf.get().getBackpackItem();
        DisplayItem wingItem = ProfilesConf.get().getWingsItem();
        DisplayItem armourItem = ProfilesConf.get().getArmourItem();

        for(int i : backpackSlots) {
            this.getInventory().setItem(i, backpackItem.getGuiItem());
            List<WardrobeItem> wardrobeItemList = ProfilesConf.get().getWardrobeCategories().get("backpack");
            setAction(i, event -> {
                new WardrobeGUI(player, "Backpacks", wardrobeItemList).open();
                event.setCancelled(true);
                return true;
            });
        }
        for(int i : wingSlots) {
            this.getInventory().setItem(i, wingItem.getGuiItem());
            List<WardrobeItem> wardrobeItemList = ProfilesConf.get().getWardrobeCategories().get("wings");
            setAction(i, event -> {
                new WardrobeGUI(player, "Wings", wardrobeItemList).open();
                event.setCancelled(true);
                return true;
            });
        }
        for(int i : armourSlots) {
            this.getInventory().setItem(i, armourItem.getGuiItem());
            List<WardrobeItem> wardrobeItemList = ProfilesConf.get().getWardrobeCategories().get("armour");
            setAction(i, event -> {
                new WardrobeGUI(player, "Armour", wardrobeItemList).open();
                event.setCancelled(true);
                return true;
            });
        }

    }

    public void open(){
        player.openInventory(this.getInventory());
    }
}
