package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WardrobeSelectionGUI extends ChestGui {

    private final Player player;

    public WardrobeSelectionGUI(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(ProfileModule.get().getWardrobeTitle())));
        init();
        add();
    }

    private void setup(int slot, DisplayItem displayItem, String command) {
        this.getInventory().setItem(slot, displayItem.getGuiItem());
        setAction(slot, event -> {
            player.performCommand(command);
            event.setCancelled(true);
            return true;
        });
    }

    private void init() {
        final ProfileModule conf = ProfileModule.get();
        setup(10, conf.getBackpackItem(), "backpackskins");
        setup(11, conf.getPickaxeItem(), "pickaxeskins");
        setup(12, conf.getWingsItem(), "wings");
        setup(13, conf.getArmourItem(), "armour");
        setup(14, conf.getNameColourItem(), "namecolour");
        setup(15, conf.getChatColourItem(), "chatcolour");
        setup(16, conf.getRankColourItem(), "rankcolour");
    }

    public void open(){
        player.openInventory(this.getInventory());
    }
}
