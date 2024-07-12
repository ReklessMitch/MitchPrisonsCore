package mitch.prisonscore.utils.configurable;

import org.bukkit.Material;

import java.util.List;

public class CommandItem extends DisplayItem {

    private String command;

    public CommandItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot, String command) {
        super(material, itemName, itemLore, customModelData, slot);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }


}
