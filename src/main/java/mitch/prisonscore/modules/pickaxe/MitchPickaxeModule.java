package mitch.prisonscore.modules.pickaxe;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.xlib.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.Getter;

import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.colls.EnchantmentConfigColl;
import mitch.prisonscore.modules.pickaxe.cmds.pickaxe.*;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayerColl;
import mitch.prisonscore.modules.pickaxe.enchants.Enchant;
import mitch.prisonscore.modules.pickaxe.enchants.EnchantmentConfig;
import mitch.prisonscore.modules.pickaxe.engines.MineBlockEvent;
import mitch.prisonscore.modules.pickaxe.engines.PickaxeMovement;
import mitch.prisonscore.utils.configurable.DisplayItem;
import mitch.prisonscore.modules.pickaxe.utils.EnchantType;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.MitchAntiCheat;
import org.bukkit.Material;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Getter
@EditorName("config")
public class MitchPickaxeModule extends Module {

    private transient Map<EnchantType, Enchant> enchants = new HashMap<>();
    private transient DecimalFormat df;

    private DecimalFormat setUpDF() {
        df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df;
    }


    private Map<String, DisplayItem> pickaxeSkins = Map.of(
            "default", new DisplayItem(Material.DIAMOND_PICKAXE, "Default", List.of("Default Pickaxe"), 0, 0),
            "emerald", new DisplayItem(Material.EMERALD, "Emerald", List.of("Emerald Pickaxe"), 1, 1));


    private void setUpEnchants() {
        Reflections reflections = new Reflections("mitch.prisonscore.modules.pickaxe.enchants.enchantments");
        Set<Class<? extends Enchant>> enchantmentClasses = reflections.getSubTypesOf(Enchant.class);

        enchantmentClasses.forEach(enchantmentClass -> {
            try {
                Enchant vEnchant = enchantmentClass.getConstructor().newInstance();
                enchants.put(vEnchant.getType(), vEnchant);

                MitchPrisonsCore.get().log("[Enchants] Registered enchant: " + vEnchant.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }


    private void registerEnchantmentConfigs() {
        RuntimeTypeAdapterFactory<EnchantmentConfig> adapterFactory = RuntimeTypeAdapterFactory.of(
                EnchantmentConfig.class, "DO_NOT_CHANGE");

        enchants.values().forEach(enchant -> adapterFactory.registerSubtype(enchant.getConfigClass(), enchant.getName()));

        MitchPrisonsCore.get().setGson(MitchPrisonsCore.get().getDummyBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .create()
        );

        MitchPrisonsCore.get().activate(EnchantmentConfigColl.class);

        enchants.values().forEach(enchant -> {
            try {
                EnchantmentConfigColl.get().attach((EnchantmentConfig) enchant.getConfigClass().getDeclaredConstructor().newInstance(), enchant.getName());
                System.out.println("Registered enchantment config: " + enchant.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Enchantment configs registered");
    }

    public Enchant getEnchantByType(EnchantType type) {
        return enchants.get(type);
    }

    public static MitchPickaxeModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.PICKAXE);
    }

    public MitchPickaxeModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.PICKAXE;
    }

    @Override
    public void enable() {
        df = setUpDF();
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                // colls
                PickaxePlayerColl.class,

                // cmds
                CmdUpgradeGUI.class, CmdToggles.class, CmdBlocks.class, CmdAutoRankup.class,
                CmdBlockTop.class,

                // listeners
                MineBlockEvent.class,
                PickaxeMovement.class
        );
        setUpEnchants();
        registerEnchantmentConfigs();
        System.out.println("Pickaxe module enabled");
    }

    @Override
    public void disable() {

    }

}
