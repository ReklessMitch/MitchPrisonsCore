package mitch.prisonscore.modules.publicmines.object;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import lombok.Setter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.booster.configs.BoosterPlayer;
import mitch.prisonscore.modules.booster.objects.Booster;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Mine {

    private MineTier tier;
    private SerLoc spawnPoint;
    private SerLoc min;
    private SerLoc max;
    private List<Material> blocks;
    private long volume;
    private transient int volumeMined;
    private String worldName;

    public Mine(SerLoc spawnPoint, SerLoc min, SerLoc max, List<Material> blocks, MineTier tier, String worldName) {
        this.spawnPoint = spawnPoint;
        this.min = min;
        this.max = max;
        this.blocks = blocks;
        this.volume = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3()).getVolume();
        this.volumeMined = 0;
        this.tier = tier;
        this.worldName = worldName;
    }

    public void teleportToMine(Player player){
        player.teleport(spawnPoint.toLocation(this));
    }

    private Collection<Player> getPlayersInRadius(){
        return spawnPoint.toLocation(this).getNearbyPlayers(250);
    }

    public boolean isInMine(BlockVector3 blockVector3) {
        return blockVector3.containedWithin(min.toBlockVector3(), max.toBlockVector3());
    }

    public boolean isInMine(Location location) {
        return location.toVector().isInAABB(min.toLocation(this).toVector(), max.toLocation(this).toVector());
    }

    public void reset(){
        World world = FaweAPI.getWorld(worldName);
        volumeMined = 0;
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)){
            final Region cub = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3());
            final RandomPattern randomPattern = new RandomPattern();
            final BlockType beacon = BlockTypes.get(Material.BEACON.name().toLowerCase());
            randomPattern.add(beacon, 0.001);
            blocks.forEach(blockType -> randomPattern.add(BlockTypes.get(blockType.name().toLowerCase()), 0.98/ blocks.size()));
            editSession.setBlocks(cub, randomPattern);
        }

        getPlayersInRadius().forEach(player -> player.teleport(spawnPoint.toLocation(this)));
    }


    public int getBlocksOnYLayer(Player player, int y){
        Logger logger = MitchPrisonsCore.get().getLogger();
        logger.log(Level.INFO, "Getting blocks on Y layer " + y + " in mine " + tier.name() + " for player " + player.getName());
        final BlockVector3 minV = min.toBlockVector3().withY(y);
        final BlockVector3 maxV = max.toBlockVector3().withY(y);
        logger.log(Level.INFO, "Min: " + minV.toString() + " Max: " + maxV.toString());

        return getBeaconsAndBlocksInRegion(player, new CuboidRegion(minV, maxV));
    }

    public int getExplosiveBlocks(Player player, Location location, int radius){
        return getBeaconsAndBlocksInRegion(player, new EllipsoidRegion(FaweAPI.getWorld(worldName), BlockVector3.at(location.getX(), location.getY(), location.getZ()), Vector3.at(radius, radius, radius)));
    }

    private BigInteger multiplyBeaconBooster(Player player, int beacons){
        final Booster beaconBooster = BoosterPlayer.get(player.getUniqueId()).getActiveBeaconBooster();
        if(beaconBooster != null){
            beacons *= beaconBooster.getMultiplier();
        }
        return BigInteger.valueOf(beacons);
    }

    private void volumeMinedCheck(){
        MitchPrisonsCore.get().getLogger().log(Level.INFO, "Volume mined: " + volumeMined + " Volume: " + volume);
        if(volumeMined >= volume * 0.7){
            reset();
        }
    }

    public int getBeaconsAndBlocksInRegion(Player player, Region region){
        int beacons = 0;
        int blocksAmt = 0;

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(worldName))) {
            MitchPrisonsCore.get().getLogger().log(Level.INFO, "Getting blocks in region");
            for (BlockVector3 vector : region) {
                if (!isInMine(vector)) continue;
                BlockType type = editSession.getBlock(vector).getBlockType();
                if (type.equals(BlockTypes.AIR)) continue;
                if (type.equals(BlockTypes.BEACON)) {
                    beacons++;
                }
                blocksAmt++;
                editSession.setBlock(vector, BlockTypes.AIR);

            }
        }

        volumeMined += blocksAmt;
        ProfilePlayer.get(player.getUniqueId()).addCurrency(Currency.BEACON, multiplyBeaconBooster(player, beacons));
        volumeMinedCheck();
        return blocksAmt;
    }

    public int apocalypse(Player player) {
        final BlockVector3 minV = min.toBlockVector3().withY(97);
        final BlockVector3 maxV = max.toBlockVector3().withY(97);

        final Random random = MitchPrisonsCore.get().getRandom();

        int totalBlocks = 0;
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(maxV.getBlockX() - minV.getBlockX() + 1) + minV.getBlockX();
            int z = random.nextInt(maxV.getBlockZ() - minV.getBlockZ() + 1) + minV.getBlockZ();
            totalBlocks += getBeaconsAndBlocksInRegion(player, new CuboidRegion(BlockVector3.at(x, 47, z), BlockVector3.at(x, 97, z)));
        }

        return totalBlocks;
    }


    public Region getBlocksInSquareRaduis(Location centerLocation, int radius){

        final BlockVector3 minPoint = BlockVector3.at(centerLocation.getBlockX() - radius, centerLocation.getBlockY() - radius, centerLocation.getBlockZ() - radius);
        final BlockVector3 maxPoint = BlockVector3.at(centerLocation.getBlockX() + radius, centerLocation.getBlockY() + radius, centerLocation.getBlockZ() + radius);

        return new CuboidRegion(minPoint, maxPoint);
    }

    public void getBlocksOnXAxis(Player player, int xAxis, int y, int levelsToGoDown){
        final BlockVector3 minV = min.toBlockVector3().withX(xAxis).clampY(y - levelsToGoDown, y);
        final BlockVector3 maxV = max.toBlockVector3().withX(xAxis).clampY(y - levelsToGoDown, y);

        getBeaconsAndBlocksInRegion(player, new CuboidRegion(minV, maxV));

    }

}
