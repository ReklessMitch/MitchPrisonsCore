package mitch.prisonscore.modules.cell.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.DisplayItemCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CellGUI extends ChestGui {

    private final List<DisplayItemCommand> guiItems;

    public CellGUI() {
        guiItems = CellModule.get().getGuiItems();
        setInventory(Bukkit.createInventory(null, CellModule.get().getSize(), "Cell Menu"));
        init();
        add();
    }

    public void init() {
        guiItems.forEach(item -> {
            getInventory().setItem(item.getSlot(), item.getGuiItem());
            this.setAction(item.getSlot(), event -> {
                item.runCommand((Player) event.getWhoClicked());
                return true;
            });
        });
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

}
