package mitch.prisonscore.modules.profile.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import mitch.prisonscore.modules.profile.object.WardrobeItem;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class WardrobeGUI extends ChestGui {

    private final List<WardrobeItem> items;
    private final Player player;

    public WardrobeGUI(Player player, String name, List<WardrobeItem> items){
        setInventory(Bukkit.createInventory(null, 54, name));
        this.items = items;
        this.player = player;
        populate();
        add();
    }

    public void populate(){
        for(int i = 0; i < items.size(); i++){
            WardrobeItem item = items.get(i);
            getInventory().setItem(i, item.getGuiItem());
            setAction(i, event -> {
                if(!player.hasPermission(item.getPermission())){
                    MessageUtils.sendMessage(player, LangConf.get().getWardrobeNoPermission());
                    event.setCancelled(true);
                    return true;
                }
                player.performCommand(item.getCommand());
                event.setCancelled(true);
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
