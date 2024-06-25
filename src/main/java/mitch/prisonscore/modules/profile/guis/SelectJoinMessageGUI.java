package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.ProfileModule;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.utils.ItemCreator;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SelectJoinMessageGUI extends ChestGui{

    private final ProfilePlayer profilePlayer;
    private final Player player;

    public SelectJoinMessageGUI(Player player) {
        setInventory(Bukkit.createInventory(null, 45, MessageUtils.colorize("<green>Select Join Message")));
        this.profilePlayer = ProfilePlayer.get(player);
        this.player = player;
        add();
        refresh();
    }

    private void refresh(){
        getInventory().setItem(4, ItemCreator.createItem(Material.DIAMOND, 1, 0,
                "<green>Current Join Message", profilePlayer.getJoinMessage()));
        int i = 9;
        for(String message : ProfileModule.get().getJoinMessages()){
            getInventory().setItem(i, ItemCreator.createItem(Material.PAPER, 1, 0, "<green>" + message));
            setAction(i, event -> {
                event.setCancelled(true);
                profilePlayer.setJoinMessage(message);
                MessageUtils.sendMessage(player, "<green>Join message set to: " + message);
                refresh();
                profilePlayer.changed();
                return true;
            });
            i++;

        }
    }


    public void open(){
        player.openInventory(getInventory());
    }

}
