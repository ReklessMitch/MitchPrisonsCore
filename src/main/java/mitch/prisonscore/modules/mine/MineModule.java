package mitch.prisonscore.modules.mine;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.cmds.admin.CmdMineIncrease;
import mitch.prisonscore.modules.mine.cmds.admin.CmdMineResetTest;
import mitch.prisonscore.modules.mine.cmds.def.CmdMineGO;
import mitch.prisonscore.modules.mine.cmds.def.CmdSetBlock;
import mitch.prisonscore.modules.mine.configs.PlayerMineColl;
import mitch.prisonscore.modules.mine.cmds.def.CmdMine;
import mitch.prisonscore.modules.mine.engine.MineEvents;
import mitch.prisonscore.modules.mine.placeholders.MinePlaceholders;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;

import java.util.Set;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class MineModule extends Module {
    private int mineSizeRadius = 40;
    private int mineYLevel = 107;
    private SerLoc mineOffset = new SerLoc(-135, 19, -48);
    private int mineBoosterCost = 1000;
    private int mineBoosterMax = 10;
    private int maxMineSize = 100;
    private int startValue = 5000;
    private int increaseAmount = 1000;
    private double increment = 3.2;
    private String schematicName = "mine.schematic";
    private int mineSizeY = 70;

    private Set<Material> blockMap = Set.of(Material.STONE);

    public long getNextMineLevelBlockRequirement(int level){
        return (long) (startValue + (increaseAmount * Math.pow(level - 9.0, increment)));
    }

    public static MineModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.MINES);
    }

    public MineModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.MINES;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                PlayerMineColl.class,

                // cmds
                CmdMine.class, CmdMineResetTest.class, CmdMineIncrease.class, CmdMineGO.class,

                // events
                MineEvents.class
        );
        new MinePlaceholders().register();
    }


    @Override
    public void disable() {

    }

}
