package me.reklessmitch.mitchprisonscore.mitchbackpack.engine;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchbackpack.gui.UpgradeBackpackGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.BEACON)){
                event.setCancelled(true);
            }
            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.PAPER)){
                new UpgradeBackpackGUI(event.getPlayer()).open();
                event.setCancelled(true);
            }
        }
    }
}
