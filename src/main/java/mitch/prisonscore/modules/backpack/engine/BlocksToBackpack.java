package mitch.prisonscore.modules.backpack.engine;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.modules.backpack.config.BackpackPlayer;
import mitch.prisonscore.modules.battlepass.events.BlocksMinedEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import org.bukkit.event.EventHandler;

import java.math.BigInteger;

public class BlocksToBackpack extends Engine {

    private static BlocksToBackpack i = new BlocksToBackpack();
    public static BlocksToBackpack get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void blocksToAdd(BlocksMinedEvent e){
        long startAmount = e.getBlocksBroken();
        PickaxePlayer ppickaxe = PickaxePlayer.get(e.getPlayer().getUniqueId());
        int fortuneLevel = ppickaxe.getEnchants().get(EnchantType.FORTUNE);
        double fortuneMulti = (double) fortuneLevel / 1000;
        if(fortuneLevel > 0) {
            startAmount *= 1 + fortuneMulti;
        }
        BackpackPlayer.get(e.getPlayer().getUniqueId()).add(BigInteger.valueOf(startAmount));
    }
}
