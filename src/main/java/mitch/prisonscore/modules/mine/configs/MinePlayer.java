package mitch.prisonscore.modules.mine.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.google.common.collect.Lists;
import com.massivecraft.massivecore.store.SenderEntity;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.utils.MineType;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Getter
@Setter
@SuppressWarnings("FieldMayBeFinal")
public class MinePlayer extends SenderEntity<MinePlayer> {

    private transient MineModule conf = MineModule.get();

    private boolean hasPrivateMine = false;
    private int mineNumber = 0;
    private Map<MineType, Mine> mines = getAllMines();
    private SerLoc spawnPoint = new SerLoc(mineNumber * 600, 55, 0);

    private Map<MineType, Mine> getAllMines() {
        Map<MineType, Mine> tempMines = new HashMap<>();
        int offset = mineNumber * 600;

        tempMines.put(MineType.JUNGLE, new Mine(
                new SerLoc(offset - 4, 52, -105),
                new SerLoc(offset -18, 21, -139),
                new SerLoc(offset + 9, 51, -112),
                Lists.newArrayList(Material.STONE),
                MineTier.TIER1,
                "privatemines"));

        tempMines.put(MineType.DESERT, new Mine(
                new SerLoc(offset + 98, 52, 60),
                new SerLoc(offset + 101, -8, 31),
                new SerLoc(offset + 158, 51, 88),
                Lists.newArrayList(Material.STONE),
                MineTier.TIER2,
                "privatemines"));

        tempMines.put(MineType.AQUARIUM, new Mine(
                new SerLoc(offset + 118, 52, -60),
                new SerLoc(offset + 97, 7, -109),
                new SerLoc(offset + 139, 51, -67),
                Lists.newArrayList(Material.STONE),
                MineTier.TIER3,
                "privatemines"));

        tempMines.put(MineType.WINTER, new Mine(
                new SerLoc(offset - 1, 52, 115),
                new SerLoc(offset - 38, -23, 119),
                new SerLoc(offset + 34, 51, 191),
                Lists.newArrayList(Material.STONE),
                MineTier.TIER4,
                "privatemines"));

        tempMines.put(MineType.MAGICAL, new Mine(
                new SerLoc(offset - 113, 52, 1),
                new SerLoc(offset - 215, -47, -40),
                new SerLoc(offset - 118, 51, 57),
                Lists.newArrayList(Material.STONE),
                MineTier.TIER5,
                "privatemines"));

        return tempMines;
    }

    public static MinePlayer get(Object oid) {
        return PlayerMineColl.get().get(oid);
    }
    
    @Override
    public MinePlayer load(@NotNull MinePlayer that)
    {
        super.load(that);
        return this;
    }

    public Mine getHighestMine(){
        return mines.get(MineModule.get().getHighestMineType(getPlayer()));
    }

    public void teleportToMine(MineType type) {
        mines.get(type).teleportToMine(getPlayer());
    }

    public void teleportToSpawnPoint() {
        getPlayer().teleport(spawnPoint.toLocation("privatemines"));
    }

    public void getMineValues(int minesGiven) {
        mineNumber = minesGiven;
        spawnPoint = new SerLoc(mineNumber * 600, 55, 0);
        mines = getAllMines();
        hasPrivateMine = true;
        resetAllMines();
        changed();
    }

    public void resetAllMines(){
        mines.forEach((mineType, mine) -> mine.reset());
    }

    public Mine getMine(Location location) {
        for (Mine mine : mines.values()) {
            if (mine.isInMine(location)) {
                return mine;
            }
        }
        return null;
    }
}
