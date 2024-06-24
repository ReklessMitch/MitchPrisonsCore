package mitch.prisonscore.modules.crates;

import com.google.common.collect.Lists;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.crates.cmds.CmdCrates;
import mitch.prisonscore.modules.crates.configs.CratePlayerColl;
import mitch.prisonscore.modules.crates.objects.Crate;
import mitch.prisonscore.modules.crates.objects.CrateReward;
import mitch.prisonscore.modules.crates.placeholders.CratePlaceholders;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class CratesModule extends Module {

    private List<Crate> crates = Lists.newArrayList(new Crate("default", 27, Lists.newArrayList(new CrateReward(
            new DisplayItem(Material.DIAMOND_BLOCK, "Diamond Block", Lists.newArrayList("&7This is a diamond block!"), 0, 1),
            0.75,
            Lists.newArrayList("give %player% diamond 1"), Lists.newArrayList("%player% has won a diamond block!"), Lists.newArrayList(""))
    ), null));

    private Map<String, DisplayItem> crateGUIItems = Map.of("default",
            new DisplayItem(Material.CHEST, "Default Crate",
                    Lists.newArrayList("&7This is a default crate!"), 0, 1));

    public static CratesModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.CRATES);
    }

    public CratesModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.CRATES;
    }

    public Crate getCrate(String name) {
        return crates.stream().filter(crate -> crate.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                CratePlayerColl.class,

                // cmds
                CmdCrates.class

        );
        new CratePlaceholders().register();
    }

    @Override
    public void disable() {

    }
}
