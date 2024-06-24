package mitch.prisonscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.logging.Logger;


public class CleanroomChunkGenerator extends ChunkGenerator {
    private BlockData[] layerBlock;
    private int[] layerHeight;

    public CleanroomChunkGenerator() {
        layerBlock = new BlockData[0];
        layerHeight = new int[0];

    }
    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        int y = -64;
        for (int i = 0; i < layerBlock.length; i++) {
            chunk.setRegion(0, y, 0, 16, y + layerHeight[i], 16, layerBlock[i]);
            y += layerHeight[i];
        }

        return chunk;
    }

    @Override
    public Location getFixedSpawnLocation(World world, @NotNull Random random) {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0);
        }

        int highestBlock = world.getHighestBlockYAt(0, 0);

        if ((highestBlock <= 0) && (world.getBlockAt(0, 0, 0).getType() == Material.AIR))
        {
            return new Location(world, 0, 64, 0);
        }

        return new Location(world, 0, highestBlock, 0);
    }
}