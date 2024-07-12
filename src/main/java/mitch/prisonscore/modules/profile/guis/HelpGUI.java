package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.utils.configurable.CommandItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpGUI extends ChestGui {

    private final Player player;

    public HelpGUI(@NotNull  Player player) {
        this.player = player;
        this.setInventory(Bukkit.createInventory(null, 27, "Help"));
        setup();
        add();
    }

    private void setup() {
        List<CommandItem> items = ProfileModule.get().getHelpMenuItems();
        for (CommandItem item : items) {
            this.getInventory().setItem(item.getSlot(), item.getGuiItem());
            setAction(item.getSlot(), event -> {
                player.performCommand(item.getCommand());
                event.setCancelled(true);
                return true;
            });

        }
    }

    public void open(){
        player.openInventory(this.getInventory());
    }
}
