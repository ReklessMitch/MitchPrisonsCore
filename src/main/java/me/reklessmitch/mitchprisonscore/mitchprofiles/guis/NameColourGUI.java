package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.utils.ItemCreator;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import me.reklessmitch.mitchprisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NameColourGUI extends ChestGui {

    public NameColourGUI() {
        this.setInventory(Bukkit.createInventory(null, 27, MessageUtils.colorize(LangConf.get().getNameColourTitle())));
        add();
        setupGUI();
    }

    private void setupGUI(){
        final Map<String, String> permToColour = ProfilesConf.get().getNameColoursPermToColour();
        final List<String> perms = new ArrayList<>(permToColour.keySet());
        for (int i = 0; i < perms.size(); i++) {
            final String perm = perms.get(i);
            final String colour = permToColour.get(perm);
            final ItemStack item = ItemCreator.createItem(
                    Material.PAPER, 1, 0, perm + " " + i, List.of("<grey>Click to select this name colour"));
            this.getInventory().setItem(i, item);
            setAction(i, event -> {
                event.setCancelled(true);
                if (event.getWhoClicked().hasPermission(perm)) {
                    MessageUtils.sendMessage(event.getWhoClicked(), "<green>Name colour set to " + colour);
                    ProfilePlayer.get(event.getWhoClicked().getUniqueId()).setCurrentNameColour(perm);
                } else {
                    MessageUtils.sendMessage(event.getWhoClicked(), "<red>You do not have permission to use this name colour");
                }
                return true;
            });
        }
    }

    @Override
    public void open(Player player){
        player.openInventory(this.getInventory());
    }

}
