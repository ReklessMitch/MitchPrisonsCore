package me.reklessmitch.mitchprisonscore.mitchpickaxe.engines;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.gui.UpgradeGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
public class PickaxeMovement extends Engine {

    private static PickaxeMovement i = new PickaxeMovement();
    public static PickaxeMovement get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void onFirstLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        PPickaxe ppickaxe = PPickaxe.get(player.getUniqueId());
        player.getInventory().setItem(0, ppickaxe.getPickaxeGuiItem());
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        // @TODO Check if its a da pickaxe
        if(!MUtil.isPickaxe(e.getPlayer().getInventory().getItemInMainHand().getType())) return;
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            new UpgradeGUI(e.getPlayer()).open();
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE)){
            e.setCancelled(true);
            e.getPlayer().sendMessage("<red>You cannot drop your pickaxe!");
        }
        if(e.getItemDrop().getItemStack().getType().equals(Material.DRAGON_EGG)){
            e.setCancelled(true);
            e.getPlayer().sendMessage("<red>You cannot drop your backpack!");
        }
    }

}
