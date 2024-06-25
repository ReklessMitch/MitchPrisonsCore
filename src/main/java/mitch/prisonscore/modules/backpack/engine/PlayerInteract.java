package mitch.prisonscore.modules.backpack.engine;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.backpack.config.BackpackPlayer;
import mitch.prisonscore.modules.backpack.gui.UpgradeBackpackGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerInteract extends Engine {

    private static PlayerInteract i = new PlayerInteract();
    public static PlayerInteract get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e){
        BackpackPlayer.get(e.getPlayer().getUniqueId()).set();
    }

    @EventHandler(ignoreCancelled = true)
    public void offHandKeybindPressed(PlayerSwapHandItemsEvent event){
        event.setCancelled(true);
        new UpgradeBackpackGUI(event.getPlayer()).open();
    }

    @EventHandler
    public void onPlayerInteractDuringGUI(InventoryMoveItemEvent event){
        if(event.getItem().getItemMeta().getPersistentDataContainer().has(
                MitchPrisonsCore.get().getNoMove())){
            event.setCancelled(true);
        }
    }

    // Re-enable this if i ever get around to adding a cmd to set your backpack slot
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event){
//        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
//            if(event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.BEACON)){
//                event.setCancelled(true);
//            }
//            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.PAPER)){
//                new UpgradeBackpackGUI(event.getPlayer()).open();
//                event.setCancelled(true);
//            }
//        }
//    }
}
