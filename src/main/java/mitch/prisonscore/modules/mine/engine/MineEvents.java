package mitch.prisonscore.modules.mine.engine;

import com.massivecraft.massivecore.Engine;
import com.sk89q.worldedit.math.BlockVector3;
import mitch.prisonscore.modules.battlepass.events.BlocksMinedEvent;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.publicmines.PublicMinesModule;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.MessageUtils;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MineEvents extends Engine {

    private static MineEvents i = new MineEvents();
    public static MineEvents get() { return i; }


    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e){
        String worldName = e.getPlayer().getWorld().getName();
        Mine mine;
        if(worldName.equals("privatemines")){
            mine = MinePlayer.get(e.getPlayer().getUniqueId()).getMine(e.getBlock().getLocation());
        }
        else if(worldName.equals("publicmines")){
            mine = PublicMinesModule.get().getMineAtLocation(e.getBlock().getLocation());
        }
        else return;

        if(mine == null){
            MessageUtils.sendMessage(e.getPlayer(), "<red>You can only break blocks in your mine");
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);
        Block block = e.getBlock();
        block.setType(Material.AIR);
        BlockInPmineBrokeEvent event = new BlockInPmineBrokeEvent(e.getPlayer(), mine, e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }


    @EventHandler(ignoreCancelled = true)
    public void onTeleportToMine(PlayerChangedWorldEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        MinePlayer playerMine = MinePlayer.get(e.getPlayer().getUniqueId());
        WorldBorder playerWorldBorder = Bukkit.createWorldBorder();

        // @TODO: Fix this
//
//        playerWorldBorder.setCenter(playerMine.getMiddleLocation().toLocation());
//        playerWorldBorder.setSize(250);
//        playerWorldBorder.setDamageAmount(1.0);
//        playerWorldBorder.setWarningDistance(1);
//        e.getPlayer().setWorldBorder(playerWorldBorder);
    }

    
    /**
     * Mine Upgrade based on blocks mined - Deprecated
     */
//    @EventHandler(ignoreCancelled = true)
//    public void mineUpgradeCheck(BlocksMinedEvent e){
//        MinePlayer playerMine = MinePlayer.get(e.getPlayer().getUniqueId());
//        long blocksMined = PickaxePlayer.get(e.getPlayer().getUniqueId()).getBlocksBroken();
//        if(playerMine.getSize() >= MineModule.get().getMaxMineSize()) return;
//        if(MineModule.get().getNextMineLevelBlockRequirement(playerMine.getSize()) <= blocksMined){
//            playerMine.upgradeSize(1, false);
//        }
//
//    }
}
