package mitch.prisonscore.modules.backpack;

import com.google.common.collect.Lists;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.backpack.cmds.base.CmdBackpack;
import mitch.prisonscore.modules.backpack.cmds.base.CmdSell;
import mitch.prisonscore.modules.backpack.config.BackPackPlayerColl;
import mitch.prisonscore.modules.backpack.engine.BlocksToBackpack;
import mitch.prisonscore.modules.backpack.engine.PlayerInteract;
import mitch.prisonscore.modules.backpack.placeholders.BackpackPlaceholders;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.configurable.FormatItem;
import org.bukkit.Material;

import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class BackpackModule extends Module {
    private int startBackPackSize = 200;
    private int slotPriceIncreasePerSize = 5;
    private int autoSellCost = 1000000;

    private Map<String, DisplayItem> backpackSkins = Map.of(
            "default", new DisplayItem(Material.DIAMOND_PICKAXE, "Default", Lists.newArrayList("default pickaxe"), 0, 0),
            "emerald", new DisplayItem(Material.EMERALD, "Emerald", Lists.newArrayList("emerald pickaxe"), 1, 1));

    private FormatItem backpackUpgradeItem =
            new FormatItem(Material.PAPER, "<redgrad>Upgrade Backpack <amount>", Lists.newArrayList("Cost <cost>"),
                    100);

    private FormatItem backpackItem =
            new FormatItem(Material.PAPER, "<redgrad>Backpack", Lists.newArrayList("Size <size>", "AutoSell: <autosell>"),
                    1);


    public static BackpackModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.BACKPACK);
    }

    public BackpackModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.BACKPACK;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                BackPackPlayerColl.class,

                // cmds
                CmdSell.class, CmdBackpack.class,

                // events
                BlocksToBackpack.class,
                PlayerInteract.class
        );
        new BackpackPlaceholders().register();
    }

    @Override
    public void disable() {

    }
}
