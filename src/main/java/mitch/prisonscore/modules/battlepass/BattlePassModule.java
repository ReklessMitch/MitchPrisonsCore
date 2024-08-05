package mitch.prisonscore.modules.battlepass;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.battlepass.config.PassPlayerColl;
import mitch.prisonscore.modules.battlepass.cmds.CmdPass;
import mitch.prisonscore.modules.battlepass.engines.UpgradeEvent;
import mitch.prisonscore.modules.battlepass.object.Reward;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.configurable.CommandItem;
import mitch.prisonscore.utils.configurable.DisplayItem;
import org.bukkit.Material;
import org.bukkit.command.Command;

import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
@SuppressWarnings("FieldMayBeFinal")
public class BattlePassModule extends Module {
    private int maxLevel = 100;
    private int creditsToBuyPremium = 5000;
    Map<Integer, List<Reward>> freeRewards = Map.of(1, List.of(new Reward("test", List.of("bc %player% reward"),
            List.of("bc %player% reward"))));
    Map<Integer, List<Reward>> paidRewards = Map.of(1, List.of(new Reward("test", List.of("bc %player% reward"),
            List.of("bc %player% reward"))));
    Map<Integer, Long> blocksPerLevel =
            Map.of(1, 500L, 2, 2500L, 3, 10000L, 4, 50000L);

    private List<CommandItem> rewardGUIItems = List.of(
            new CommandItem(Material.DIAMOND_BLOCK, "Reward",
                    List.of("bc %player% reward"), 0, 0, "reward"));
    private DisplayItem premiumPassItem = new CommandItem(Material.DIAMOND_BLOCK, "Premium Pass",
            List.of("bc %player% reward"), 0, 0, "reward"
    );
    public static BattlePassModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.BATTLEPASS);
    }

    public BattlePassModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.BATTLEPASS;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                PassPlayerColl.class,
                // cmds
                CmdPass.class,


                // events
                UpgradeEvent.class

        );
    }

    @Override
    public void disable() {

    }
}
