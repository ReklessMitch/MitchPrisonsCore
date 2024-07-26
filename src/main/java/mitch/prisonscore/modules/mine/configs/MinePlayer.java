package mitch.prisonscore.modules.mine.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.google.common.collect.Lists;
import com.massivecraft.massivecore.store.SenderEntity;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class MinePlayer extends SenderEntity<MinePlayer> {

    private transient MineModule conf = MineModule.get();
    private Mine mine = new Mine("default",
            new SerLoc(0,100,0),
            new SerLoc(0,0,0),
            new SerLoc(0,0,0),
            Lists.newArrayList(Material.STONE),
            MineTier.NOT_A_TIER,
            "privatemines"
            );

    private int offSetX = 400 * PlayerMineColl.get().getAll().size();

    public void getOffSetsBasedOnTier(){
        // offset for T4 mines
        mine.setSpawnPoint(new SerLoc(offSetX, 100, 0));
        if (mine.getTier() == MineTier.TIER4){
            mine.setMin(new SerLoc(-137 + offSetX, 30, -49));
            mine.setMax(new SerLoc(-38 + offSetX, 90, 50));
        }
        // 80x80
        if (mine.getTier() == MineTier.TIER3){
            mine.setMin(new SerLoc(-127 + offSetX, 30, -39));
            mine.setMax(new SerLoc(-28 + offSetX, 90, 40));
        }
        // 65x65
        if (mine.getTier() == MineTier.TIER2){
            mine.setMin(new SerLoc(-120 + offSetX, 30, -32));
            mine.setMax(new SerLoc(-21 + offSetX, 90, 33));
        }
        // 50x50
        if (mine.getTier() == MineTier.TIER1) {
            mine.setMin(new SerLoc(-112 + offSetX, 30, -24));
            mine.setMax(new SerLoc(-13 + offSetX, 90, 25));
        }
    }

    public void generateSchematic(){
        getOffSetsBasedOnTier();
        final World world = FaweAPI.getWorld("privatemines");

        final File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + conf.getSchematicName());

        try {Clipboard clip = FaweAPI.load(file);
            MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () ->
                    clip.paste(world, mine.getSpawnPoint().toBlockVector3(), false,
                            false, false, null));
            try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                final Region top = new CuboidRegion(mine.getMin().toBlockVector3().withY(conf.getMineYLevel()),
                                                    mine.getMax().toBlockVector3().withY(conf.getMineYLevel()));
                editSession.setBlocks(top, BlockTypes.BEDROCK);
            }finally {
                createWallsAndFloor();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        MitchPrisonsCore.get().getLogger().log(Level.SEVERE, "Created Mine Schematic for " + getPlayer().getName());
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


    private void createWallsAndFloor(){
        final World world = FaweAPI.getWorld("privatemines");
        final SerLoc min = mine.getMin();
        final SerLoc max = mine.getMax();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            CuboidRegion region = new CuboidRegion(min.toBlockVector3().subtract(1, 1, 1),
                    max.toBlockVector3().add(1,0,1));
            editSession.makeCuboidWalls(region, BlockTypes.BEDROCK);

            Region floor = new CuboidRegion(min.toBlockVector3().withY(region.getMinimumY() - 1),
                    max.toBlockVector3().withY(region.getMinimumY() - 1));
            editSession.setBlocks(floor, BlockTypes.BEDROCK);
        }
        mine.reset();
    }

    public void increaseSize(MineTier tier) {
        int currentSize = mine.getTier().getMineSize();
        int newSize = tier.getMineSize();
        if(currentSize == newSize){
            MessageUtils.sendMessage(getPlayer(), "You already have a mine of this size!");
            return;
        }
        if(currentSize < newSize){
            mine.setTier(tier);
            int diff = newSize - currentSize / 2;
            mine.getMax().add(diff);
            mine.getMin().subtract(diff);
            changed();
            createWallsAndFloor();
        }
    }
}
