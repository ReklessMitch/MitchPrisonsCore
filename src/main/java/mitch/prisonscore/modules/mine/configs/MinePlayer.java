package mitch.prisonscore.modules.mine.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import com.sk89q.worldedit.world.block.BaseBlock;
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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class MinePlayer extends SenderEntity<MinePlayer> {

    private transient MineModule conf = MineModule.get();

    private int size = 10;
    private int offSetX = 400 * PlayerMineColl.get().getAll().size();

    private SerLoc spawnPoint = new SerLoc(offSetX, 99, 0);
    private SerLoc middleLocation = new SerLoc(offSetX + conf.getMineOffset().getX(),conf.getMineYLevel(), conf.getMineOffset().getZ());
    private SerLoc min = new SerLoc(-size, -MineModule.get().getMineSizeY(), -size).addS(middleLocation);
    private SerLoc max = new SerLoc(size, 0, size).addS(middleLocation);
    private long volume = (long) (size + 1) * (size + 1) * 70;
    private long volumeMined = 0;
    @Setter private Set<Material> blocks = Sets.newHashSet(Material.STONE);
    private int booster = 1;
    private transient World world = FaweAPI.getWorld("privatemines");

    public void testReset(){
        middleLocation = new SerLoc(offSetX + MineModule.get().getMineOffset().getX(), MineModule.get().getMineYLevel(), MineModule.get().getMineOffset().getZ());
        min = new SerLoc(-size, -MineModule.get().getMineSizeY(), -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
        reset();
    }


    public void reset(){
        volumeMined = 0;
        final Location l = getPlayer().getLocation();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)){
            final Region cub = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3());
            final RandomPattern randomPattern = new RandomPattern();
            final BlockType beacon = BlockTypes.get(Material.BEACON.name().toLowerCase());
            randomPattern.add(beacon, 0.001);
            blocks.forEach(blockType -> randomPattern.add(BlockTypes.get(blockType.name().toLowerCase()), 0.98/ blocks.size()));
            editSession.setBlocks(cub, randomPattern);
        }

        if(isInMine(BlockVector3.at(l.getX(), l.getY(), l.getZ()))){
            getPlayer().teleport(middleLocation.toLocation().add(0, 1, 0));
            MessageUtils.sendMessage(getPlayer(), LangConf.get().getMineReset());
        }
        changed();
    }

    public void removeBlock(Material material){
        if(blocks.contains(material)){
            if(blocks.size() == 1){
                MessageUtils.sendMessage(getPlayer(), "<red>You can't remove the last block!");
                return;
            }
            MessageUtils.sendMessage(getPlayer(), "<red>Block removed!");
            blocks.remove(material);
            changed();
        }else{
            MessageUtils.sendMessage(getPlayer(), "<red>Block isn't selected!");
        }
    }

    public void addBlock(Material material){
        if(!blocks.contains(material)){
            MessageUtils.sendMessage(getPlayer(), "<green>Block added!");
            blocks.add(material);
            changed();
        }else{
            MessageUtils.sendMessage(getPlayer(), "<red>Block already selected!");
        }
    }



    public void addBooster(int amount){
        booster += amount;
        MessageUtils.sendMessage(getPlayer(), LangConf.get().getMineBoosterAdded());
        changed();
    }

    public void generateSchematic(){
        final File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + conf.getSchematicName());

        try {Clipboard clip = FaweAPI.load(file);
            MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () ->
                    clip.paste(world, spawnPoint.toBlockVector3(), false,
                            false, false, null));
            int maxSize = MineModule.get().getMaxMineSize();

            try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                final Region top = new CuboidRegion(middleLocation.toBlockVector3().add(-maxSize, 0, -maxSize), middleLocation.toBlockVector3().add(maxSize, 0, maxSize));
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

    int getNewSize(int amount, boolean set){
        int maxSize = MineModule.get().getMaxMineSize();
        if(maxSize < size + amount){
            MessageUtils.sendMessage(getPlayer(), "<red><bold>Max Mine Size Reached");
            return maxSize;
        }
        if(set){
            return amount;
        }else{
            MessageUtils.sendMessage(getPlayer(), "<green><bold>Your Mine has been upgraded to size: " + size);
            return size + amount;
        }
    }

    private void createWallsAndFloor(){
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            CuboidRegion region = new CuboidRegion(min.toBlockVector3().subtract(1, 1, 1),
                    max.toBlockVector3().add(1,0,1));
            editSession.makeCuboidWalls(region, BlockTypes.BEDROCK);

            Region floor = new CuboidRegion(min.toBlockVector3().withY(region.getMinimumY() - 1),
                    max.toBlockVector3().withY(region.getMinimumY() - 1));
            editSession.setBlocks(floor, BlockTypes.BEDROCK);
        }
    }
    public void upgradeSize(int amount, boolean set) {
        size = getNewSize(amount, set);
        min = new SerLoc(-size, -70, -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
        volume = (long) (size + 1) * (size + 1) * 100;

        createWallsAndFloor();
        MessageUtils.sendMessage(sender, "<green>You have set your mine size to " + size);
        reset();
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
        int blocksAmt = 0;
        Random random = MitchPrisonsCore.get().getRandom();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            for (BlockVector3 vector : region) {
                if (!isInMine(vector)) continue;
                if(!editSession.getBlock(vector).getBlockType().equals(BlockTypes.AIR)){
                    if(random.nextDouble() > chance) continue;
                    blocksAmt++;
                    editSession.setBlock(vector, blockType);
                }
            }
        }
        return blocksAmt;
    }

    public int getBeaconsAndBlocksInRegion(Region region){
        int beacons = 0;
        int blocksAmt = 0;

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
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
        ProfilePlayer.get(getPlayer()).addCurrency(Currency.BEACON, multiplyBeaconBooster(beacons));
        volumeMinedCheck();
        return blocksAmt;
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
