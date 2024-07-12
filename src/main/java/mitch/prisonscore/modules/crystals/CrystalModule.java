package mitch.prisonscore.modules.crystals;

import com.google.common.collect.Lists;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.crystals.cmds.CmdCrystal;
import mitch.prisonscore.modules.crystals.configs.CrystalPlayerColl;
import mitch.prisonscore.modules.crystals.engines.CrystalApply;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

@Getter
public class CrystalModule extends Module {

    private final transient NamespacedKey typeKey = new NamespacedKey(MitchPrisonsCore.get(), "crystalType");
    private final transient NamespacedKey boostKey = new NamespacedKey(MitchPrisonsCore.get(), "crystalBoost");
    private final transient NamespacedKey percentKey = new NamespacedKey(MitchPrisonsCore.get(), "crystalPercent");

    private FormatItem crystalFormat = new FormatItem(
            Material.EMERALD, "<red>Crystal Information",
            Lists.newArrayList(
                    "<grey>Left Click - <green>Select",
                    "<grey>Right Click - <green>Add 1x Dust",
                    "<grey>Drop - <red>Tinker",
                    "<grey>Middle Click - <green>Add Max Dust",
                    "<grey>NUM_KEY - <green>Add NUM_KEY(x) Dust",
                    "",
                    "<red>Crystal Amount: <white><dust>"),
            5
    );

    public static CrystalModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.CRYSTALS);
    }

    public CrystalModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.CRYSTALS;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                CrystalPlayerColl.class,

                CmdCrystal.class,

                CrystalApply.class
        );
    }

    @Override
    public void disable() {

    }

}
