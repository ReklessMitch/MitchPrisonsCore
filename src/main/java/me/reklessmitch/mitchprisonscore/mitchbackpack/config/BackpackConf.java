package me.reklessmitch.mitchprisonscore.mitchbackpack.config;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class BackpackConf extends Entity<BackpackConf> {
    public static BackpackConf i;
    public static BackpackConf get() { return i; }

    private int startBackPackSize = 200;
    private int slotPriceIncreasePerSize = 5;
    private int autoSellCost = 1000000;

    private Map<String, DisplayItem> backpackSkins = Map.of(
            "default", new DisplayItem(Material.DIAMOND_PICKAXE, "Default", Lists.newArrayList("default pickaxe"), 0, 0),
            "emerald", new DisplayItem(Material.EMERALD, "Emerald", Lists.newArrayList("emerald pickaxe"), 1, 1));

}
