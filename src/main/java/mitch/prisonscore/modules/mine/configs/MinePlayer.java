package mitch.prisonscore.modules.mine.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.IdUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
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
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.profile.utils.Currency;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class MinePlayer extends SenderEntity<MinePlayer> {

    private int size = 10;
    private int offSetX = 300 * PlayerMineColl.get().getAll().size();
    private SerLoc spawnPoint = new SerLoc(offSetX, 99, 0);
    private SerLoc middleLocation = new SerLoc(offSetX + MineModule.get().getMineOffset().getX(), MineModule.get().getMineYLevel(), MineModule.get().getMineOffset().getZ());
    private SerLoc min = new SerLoc(-size, -MineModule.get().getMineSizeY(), -size).addS(middleLocation);
    private SerLoc max = new SerLoc(size, 0, size).addS(middleLocation);
    private long volume = (long) (size + 1) * (size + 1) * 100; // 50 * 2 for size radius
    private long volumeMined = 0;
    @Setter private Material block = Material.STONE;
    private int booster = 1;
    private transient World world = FaweAPI.getWorld("privatemines");

    public void testReset(){
        middleLocation = new SerLoc(offSetX + MineModule.get().getMineOffset().getX(), MineModule.get().getMineYLevel(), MineModule.get().getMineOffset().getZ());
        min = new SerLoc(-size, -MineModule.get().getMineSizeY(), -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
    }

    public void reset(){
        volumeMined = 0;
        final Location l = getPlayer().getLocation();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)){
            final Region cub = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3());
            final RandomPattern randomPattern = new RandomPattern();
            final BlockType normalBlock = BlockTypes.get(this.block.name().toLowerCase());
            final BlockType beacon = BlockTypes.get(Material.BEACON.name().toLowerCase());
            randomPattern.add(beacon, 0.001);
            randomPattern.add(normalBlock, 0.98);
            editSession.setBlocks(cub, randomPattern);
        }

        if(isInMine(BlockVector3.at(l.getX(), l.getY(), l.getZ()))){
            getPlayer().teleport(middleLocation.toLocation().add(0, 1, 0));
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getMineReset());
        }
    }


    public void addBooster(int amount){
        booster += amount;
        MessageUtils.sendMessage(getPlayer(), LangConf.get().getMineBoosterAdded());
        changed();
    }

    public void generateSchematic(){
        final File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + MineModule.get().getSchematicName());

        try {Clipboard clip = FaweAPI.load(file);
            MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () ->
                    clip.paste(world, spawnPoint.toBlockVector3(), false,
                            false, false, null));
        }catch (IOException e){
            e.printStackTrace();
        }
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

    public boolean isInMine(BlockVector3 blockVector3) {
        return blockVector3.containedWithin(min.toBlockVector3(), max.toBlockVector3());
    }

    public int getBlocksOnYLayer(int y){

        final BlockVector3 minV = min.toBlockVector3().withY(y);
        final BlockVector3 maxV = max.toBlockVector3().withY(y);

        return getBeaconsAndBlocksInRegion(new CuboidRegion(minV, maxV));
    }

    public int getExplosiveBlocks(Location location, int radius){
        return getBeaconsAndBlocksInRegion(new EllipsoidRegion(world, BlockVector3.at(location.getX(), location.getY(), location.getZ()), Vector3.at(radius, radius, radius)));
    }

    private BigInteger multiplyBeaconBooster(int beacons){
        final Booster beaconBooster = BoosterPlayer.get(getPlayer()).getActiveBeaconBooster();
        if(beaconBooster != null){
            beacons *= beaconBooster.getMultiplier();
        }
        return BigInteger.valueOf(beacons);
    }
    private void volumeMinedCheck(){
        if(volumeMined >= volume * 0.7){
            reset();
        }
    }

    public void upgradeSize(int amount, boolean set) {
        if(set){
            size = amount;
        }else{
            MessageUtils.sendMessage(getPlayer(), "<green><bold>Your Mine has been upgraded to size: " + size);
            size += amount;
        }
        min = new SerLoc(-size, -70, -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
        volume = (long) (size + 1) * (size + 1) * 100;
        reset();
        changed();
    }

    public void teleport() {
        reset();
        Location l = spawnPoint.toLocation().add(0, 1, 0);
        l.setYaw(90);
        if (IdUtil.getOfflinePlayer(id).isOnline()){
            getPlayer().teleport(l);
        }
    }

    public int replaceBlocksInRegion(Region region, BlockType blockType, double chance){
        int blocks = 0;
        Random random = MitchPrisonsCore.get().getRandom();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            for (BlockVector3 vector : region) {
                if (!isInMine(vector)) continue;
                if(!editSession.getBlock(vector).getBlockType().equals(BlockTypes.AIR)){
                    if(random.nextDouble() > chance) continue;
                    blocks++;
                    editSession.setBlock(vector, blockType);
                }
            }
        }
        return blocks;
    }

    public int getBeaconsAndBlocksInRegion(Region region){
        int beacons = 0;
        int blocks = 0;

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            for (BlockVector3 vector : region) {
                if (!isInMine(vector)) continue;
                BlockType type = editSession.getBlock(vector).getBlockType();
                if (type.equals(BlockTypes.AIR)) continue;
                if (type.equals(BlockTypes.BEACON)) {
                    beacons++;
                }
                blocks++;
                editSession.setBlock(vector, BlockTypes.AIR);

            }
        }
        volumeMined += blocks;
        ProfilePlayer.get(getPlayer()).addCurrency(Currency.BEACON, multiplyBeaconBooster(beacons));
        volumeMinedCheck();
        return blocks;
    }

    public int apocalypse() {
        final BlockVector3 minV = min.toBlockVector3().withY(97);
        final BlockVector3 maxV = max.toBlockVector3().withY(97);

        final Random random = MitchPrisonsCore.get().getRandom();

        int totalBlocks = 0;
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(maxV.getBlockX() - minV.getBlockX() + 1) + minV.getBlockX();
            int z = random.nextInt(maxV.getBlockZ() - minV.getBlockZ() + 1) + minV.getBlockZ();
            totalBlocks += getBeaconsAndBlocksInRegion(new CuboidRegion(BlockVector3.at(x, 47, z), BlockVector3.at(x, 97, z)));
        }

        return totalBlocks;
    }

//    public Location getRandomLocationOnYLayer(int y){
//        final BlockVector3 minV = min.toBlockVector3().withY(y);
//        final BlockVector3 maxV = max.toBlockVector3().withY(y);
//
//        final Random random = MitchPrisonsCore.get().getRandom();
//        int x = random.nextInt(maxV.getBlockX() - minV.getBlockX() + 1) + minV.getBlockX();
//        int z = random.nextInt(maxV.getBlockZ() - minV.getBlockZ() + 1) + minV.getBlockZ();
//
//        return new Location(Bukkit.getWorld("privatemines"), x, y, z);
//    }

    public Region getBlocksInSquareRaduis(Location centerLocation, int radius){

        final BlockVector3 minPoint = BlockVector3.at(centerLocation.getBlockX() - radius, centerLocation.getBlockY() - radius, centerLocation.getBlockZ() - radius);
        final BlockVector3 maxPoint = BlockVector3.at(centerLocation.getBlockX() + radius, centerLocation.getBlockY() + radius, centerLocation.getBlockZ() + radius);

        return new CuboidRegion(minPoint, maxPoint);
    }

    public void getBlocksOnXAxis(int xAxis, int y, int levelsToGoDown){
        final BlockVector3 minV = min.toBlockVector3().withX(xAxis).clampY(y - levelsToGoDown, y);
        final BlockVector3 maxV = max.toBlockVector3().withX(xAxis).clampY(y - levelsToGoDown, y);

        getBeaconsAndBlocksInRegion(new CuboidRegion(minV, maxV));

    }
}
