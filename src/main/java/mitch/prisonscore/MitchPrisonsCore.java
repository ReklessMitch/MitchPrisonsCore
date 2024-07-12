package mitch.prisonscore;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.GsonBuilder;
import com.massivecraft.massivecore.xlib.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.Getter;
import me.reklessmitch.mitchvoidgen.chunkgen.VoidChunkGenerator;
import mitch.prisonscore.colls.LangColl;
import mitch.prisonscore.colls.MessageConfColl;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.ModuleColl;
import mitch.prisonscore.modules.pet.entity.PetPlayer;
import mitch.prisonscore.modules.pet.entity.PetPlayerColl;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;


@Getter
public final class MitchPrisonsCore extends MassivePlugin {

    private static MitchPrisonsCore i;
    public static MitchPrisonsCore get() { return i; }

    private Leaderboard leaderboard;
    //private List<Player> awaitingChatResponse = new ArrayList<>();
    private final Map<ModuleType, Module> modulesByType;
    private GsonBuilder dummyBuilder;

    public MitchPrisonsCore() {
        i = this;
        this.versionSynchronized = false;
        this.modulesByType = Maps.newHashMap();
    }

    private void createPrivateMineWorld() {
        Bukkit.createWorld(new WorldCreator("privatemines").generator(new VoidChunkGenerator()));
    }

    private Map<UUID, ArmorStand> playerWardrobes = new HashMap<>();

    private final NamespacedKey noMove = new NamespacedKey(this, "noMove");


    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private WorldBorderApi worldBorderApi;
    private final Random random = new SecureRandom();

    @Override
    public void onEnableInner() {
        i = this;
        this.dummyBuilder = this.getGsonBuilder();
        createPrivateMineWorld();
        // Register Modules

        loadModules();
        leaderboard = new Leaderboard();

        worldBorderApi = Bukkit.getServicesManager().getRegistration(WorldBorderApi.class).getProvider();
        doAnnouncements();
        MitchAntiCheat.get().getCheckPlayersTask().runTaskTimer(this, 0, 200);
    }

    private void loadModules() {
        activate(
                LangColl.class,
                MessageConfColl.class
        );

        Reflections reflections = new Reflections("mitch.prisonscore.modules");
        Set<Class<? extends Module>> moduleClasses = reflections.getSubTypesOf(Module.class)
                .stream()
                .filter(cls -> !Modifier.isAbstract(cls.getModifiers())).collect(Collectors.toSet());

        RuntimeTypeAdapterFactory<Module> adapterFactory = RuntimeTypeAdapterFactory.of(Module.class, "type");

        Set<Module> allModules = Sets.newHashSet();
        moduleClasses.forEach(moduleClass -> {
            try {
                Bukkit.getLogger().log(Level.SEVERE, "Loading Module: " + moduleClass.getName());
                Module module = moduleClass.getDeclaredConstructor().newInstance();
                allModules.add(module);
                adapterFactory.registerSubtype(moduleClass, module.getType().name());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        setGson(this.dummyBuilder.registerTypeAdapterFactory(adapterFactory).create());
        activate(ModuleColl.class);
        ModuleColl.get().getAll().forEach(module -> {
            initModule(module);
            allModules.removeIf(otherModule -> otherModule.getType() == module.getType());
        });
        allModules.forEach(module -> {
            ModuleColl.get().attach(module, module);
            initModule(module);
        });
    }


    private void doAnnouncements(){
        final List<List<String>> announcements = LangConf.get().getAnnouncements();
        AtomicInteger iteration = new AtomicInteger();
        if(announcements == null) return;
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            announcements.get(iteration.get()).forEach(s -> Bukkit.broadcast(MessageUtils.colorize(s)));
            iteration.getAndIncrement();
            if(iteration.get() >= announcements.size()) iteration.set(0);
        }, 0, 20 * 90);
    }

    @Override
    public void onDisable() {
        i = null;
        MUtil.getOnlinePlayers().forEach(player -> {
            PetPlayer petPlayer = PetPlayerColl.get().get(player);
            if(petPlayer.isShowPet()){
                petPlayer.despawnPet();
            }
        });
        super.onDisable();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id)
    {
        return new CleanroomChunkGenerator();
    }

    public <T extends Module> T getModuleByType(ModuleType type) {
        return (T) modulesByType.get(type);
    }

    private void initModule(Module module) {
        long enableTime = System.currentTimeMillis();
        modulesByType.put(module.getType(), module);
        module.enable();
        log("[MPC] Initialized Module: " + module.getName() + " (Took " + (System.currentTimeMillis() - enableTime) + "ms)");
    }
}
