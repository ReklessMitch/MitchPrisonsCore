package mitch.prisonscore.modules.booster;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.booster.configs.BoosterPlayerColl;
import mitch.prisonscore.modules.booster.cmds.booster.CmdBooster;
import mitch.prisonscore.modules.booster.engines.BoosterInteract;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.NamespacedKey;

@Getter
@SuppressWarnings("FieldMayBeFinal")

public class BoosterModule extends Module {

    public static BoosterModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.BOOSTERS);
    }

    public BoosterModule() {
        super(true);
    }

    private final transient NamespacedKey typeKey = new NamespacedKey(MitchPrisonsCore.get(), "boosterType");
    private final transient NamespacedKey multiKey = new NamespacedKey(MitchPrisonsCore.get(), "boosterMultiplier");
    private final transient NamespacedKey durationKey = new NamespacedKey(MitchPrisonsCore.get(), "boosterDuration");

    @Override
    public ModuleType getType() {
        return ModuleType.BOOSTERS;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                BoosterPlayerColl.class,

                // cmds
                CmdBooster.class,

                // events
                BoosterInteract.class
        );
    }

    @Override
    public void disable() {

    }
}
