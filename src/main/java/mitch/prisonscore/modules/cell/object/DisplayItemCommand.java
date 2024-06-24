package mitch.prisonscore.modules.cell.object;

import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class DisplayItemCommand extends DisplayItem {

    private String command;

    public DisplayItemCommand(Material material, String itemName, List<String> itemLore, int customModelData, int slot, String command) {
        super(material, itemName, itemLore, customModelData, slot);
        this.command = command;
    }

    public void runCommand(Player player){
        if(command.equals("")) return;
        player.performCommand(command);
    }
}
