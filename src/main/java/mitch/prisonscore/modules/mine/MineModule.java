package mitch.prisonscore.modules.mine;

import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.cmds.def.CmdCreateMines;
import mitch.prisonscore.modules.mine.cmds.def.CmdGivePrivateMine;
import mitch.prisonscore.modules.mine.cmds.def.CmdMineGO;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.mine.configs.PlayerMineColl;
import mitch.prisonscore.modules.mine.cmds.def.CmdMine;
import mitch.prisonscore.modules.mine.engine.MineEvents;
import mitch.prisonscore.modules.mine.placeholders.MinePlaceholders;
import mitch.prisonscore.modules.mine.utils.MineType;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.modules.type.ModuleType;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class MineModule extends Module {
    private int mineSizeRadius = 40;
    private int mineYLevel = 107;
    //private SerLoc mineOffset = new SerLoc(-135, 19, -48);
    private int mineBoosterCost = 1000;
    private int mineBoosterMax = 10;
    //private int maxMineSize = 50;
    private int startValue = 5000;
    private int increaseAmount = 1000;
    private double increment = 3.2;
    private String schematicName = "mine.schematic";
    //private int mineSizeY = 70;
    private int minesCreated = 0;
    private int minesGiven = 0;
    private transient Map<UUID, Mine> playerToMine = Map.of();

    private Map<Integer, MineType> minRankToAccessMineType = Map.of(
            0, MineType.JUNGLE,
            50, MineType.DESERT,
            100, MineType.AQUARIUM,
            150, MineType.WINTER,
            200, MineType.MAGICAL
    );

    private Set<Material> blockMap = Set.of(Material.STONE);

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
                CmdMine.class, CmdMineGO.class, CmdCreateMines.class, CmdGivePrivateMine.class,

                // events
                MineEvents.class
        );
        new MinePlaceholders().register();
    }




    @Override
    public void disable() {

    }

    public void addMine() {
        minesCreated++;
        changed();
    }

    public MineType getHighestMineType(Player player) {
        int rank = ProfilePlayer.get(player.getUniqueId()).getRank();

        // get the highest mine the player can access
        MineType highestMineType = MineType.JUNGLE; // Default to the lowest mine type
        for (Map.Entry<Integer, MineType> entry : minRankToAccessMineType.entrySet()) {
            if (rank >= entry.getKey()) {
                highestMineType = entry.getValue();
            } else {
                break; // Since the map is sorted, no need to check further
            }
        }
        return highestMineType;
    }

    public void givePrivateMine(Player player) {
        MinePlayer minePlayer = MinePlayer.get(player.getUniqueId());
        if (minePlayer.isHasPrivateMine()){
            MessageUtils.sendMessage(player, "<red>You already have a private mine!");
            return;
        }
        MessageUtils.sendMessage(player, "<green>You have been given a private mine!");
        minesGiven++;
        minePlayer.getMineValues(minesGiven);
        changed();
    }
}
