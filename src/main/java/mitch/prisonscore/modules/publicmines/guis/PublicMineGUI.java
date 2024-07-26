package mitch.prisonscore.modules.publicmines.guis;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.publicmines.PublicMinesModule;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.Map;

public class PublicMineGUI extends ChestGui {

    private final Player player;
    int playerRank;

    public PublicMineGUI(Player player) {
        this.player = player;
        playerRank = ProfilePlayer.get(player.getUniqueId()).getRank();
        setInventory(Bukkit.createInventory(null, 27, "Public Mines"));
        addItems();
        add();
    }

    public void addItems() {
        Map<Integer, Mine> mines = PublicMinesModule.get().getPublicMines();
        // change this to a map loop instead of a for loop

        int i = 0;
        for(Map.Entry<Integer, Mine> entry : mines.entrySet()) {
            getInventory().addItem(ItemCreator.createItem(Material.STONE, 1, entry.getValue().getName(), Lists.newArrayList("" +
                    "<grey>Teleport to the " + entry.getValue().getName() + " mine.")));
            setAction(i, event -> {
                if(entry.getKey() > playerRank) {
                    MessageUtils.sendMessage(event.getWhoClicked(), "<red>You do not have access to this mine.");
                    return false;
                }
                entry.getValue().teleportToMine((Player) event.getWhoClicked());
                return true;
            });
            i++;
        }

    }

    public void open() {
        player.openInventory(getInventory());
    }



}
