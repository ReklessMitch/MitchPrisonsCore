package mitch.prisonscore.modules.crates.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.crates.objects.Crate;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CratePreview extends ChestGui {

    private final Player player;
    private final Crate crate;
    private boolean isAdmin = false;

    public CratePreview(Player player, Crate crate) {
        this.player = player;
        this.crate = crate;
        if(player.hasPermission("mpc.admin")){
            isAdmin = true;
        }
        setInventory(Bukkit.createInventory(null, crate.getSize(), crate.getName()));
        setup();
        add();
    }

    private void setup() {
        crate.getRewards().forEach(reward -> {
            // Add reward to inventory
            DisplayItem item = reward.getDisplayItem();
            int slot = item.getSlot();

            getInventory().setItem(slot, item.getGuiItem());
            if(!isAdmin) return;
            setAction(slot, event -> {
                reward.executeReward(player, crate.getName());
                return true;
            });

        });

    }

    public void open() {
        player.openInventory(getInventory());
    }

}
