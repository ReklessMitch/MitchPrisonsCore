package mitch.prisonscore.modules.mine.engine;

import com.massivecraft.massivecore.Engine;
import com.sk89q.worldedit.math.BlockVector3;
import mitch.prisonscore.modules.battlepass.events.BlocksMinedEvent;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.utils.MessageUtils;
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
    public void onJoin(PlayerJoinEvent e){
        System.out.println("Player joined");
        if(e.getPlayer().hasPlayedBefore()) {
            return;
        }
        System.out.println("Player has not played before");
        MinePlayer playerMine = MinePlayer.get(e.getPlayer().getUniqueId());
        playerMine.generateSchematic();
        playerMine.changed();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        e.setCancelled(true);
        Block block = e.getBlock();
        BlockVector3 blockVector3 = BlockVector3.at(block.getX(), block.getY(), block.getZ());
        MinePlayer playerMine = MinePlayer.get(e.getPlayer().getUniqueId());
        if(!playerMine.isInMine(blockVector3)){
            MessageUtils.sendMessage(e.getPlayer(), "<red>You can only break blocks in your mine");
            return;
        }
        block.setType(Material.AIR);
        BlockInPmineBrokeEvent event = new BlockInPmineBrokeEvent(e.getPlayer(), playerMine, e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }


    @EventHandler(ignoreCancelled = true)
    public void onTeleportToMine(PlayerChangedWorldEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        MinePlayer playerMine = MinePlayer.get(e.getPlayer().getUniqueId());
//        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(), () ->
//                spoofWorldBorder(e.getPlayer(), playerMine.getMiddleLocation().toLocation(), 124), 40);
    }

//    public void spoofWorldBorder(Player player, Location center, double size) {
//        MitchPrisonsCore.get().getWorldBorderApi().setBorder(player, size, center);
//    }


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
