package mitch.prisonscore.modules.battlepass.engines;

import com.massivecraft.massivecore.Engine;
import mitch.prisonscore.modules.battlepass.BattlePassModule;
import mitch.prisonscore.modules.battlepass.config.PassPlayer;
import mitch.prisonscore.modules.battlepass.events.BlocksMinedEvent;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;

public class UpgradeEvent extends Engine {

    public static UpgradeEvent i = new UpgradeEvent();
    public static UpgradeEvent get(){
        return i;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUpgrade(BlocksMinedEvent e){
        final PassPlayer pp = PassPlayer.get(e.getPlayer().getUniqueId());
        if(pp.getLevel() == BattlePassModule.get().getMaxLevel()) return;
        long blocksBroken = PickaxePlayer.get(e.getPlayer().getUniqueId()).getBlocksBroken();
        long blocksPerLevel = BattlePassModule.get().getBlocksPerLevel().get(pp.getLevel() + 1);
        if(blocksPerLevel <= blocksBroken){
            pp.addLevel();
            final TagResolver levelResolver = Placeholder.parsed("level", "" + pp.getLevel());
            MessageUtils.sendMessage(e.getPlayer(), LangConf.get().getBattlePassLevelUp(), levelResolver);
        }
    }
}
