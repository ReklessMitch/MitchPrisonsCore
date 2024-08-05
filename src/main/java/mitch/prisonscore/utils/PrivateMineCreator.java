package mitch.prisonscore.utils;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.mine.MineModule;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

public class PrivateMineCreator {

    private static final Queue<Runnable> mineQueue = new LinkedList<>();
    private static boolean isProcessing = false;

    public static void createMines(int amount) {
        for (int i = 0; i < amount; i++) {
            addMineToQueue();
        }
        processQueue();
    }

    private static void addMineToQueue() {
        mineQueue.add(() -> {
            final World world = FaweAPI.getWorld("privatemines");
            MineModule mineModule = MineModule.get();
            BlockVector3 spawnPoint = BlockVector3.at((mineModule.getMinesCreated() + 1) * 600, 55, 0);
            final File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + mineModule.getSchematicName());

            try {
                Clipboard clip = FaweAPI.load(file);
                MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () -> {
                    clip.paste(world, spawnPoint, false, false, false, null);
                    MitchPrisonsCore.get().getLogger().log(Level.SEVERE, "Created Mine Schematic");
                    mineModule.addMine();
                    processQueue();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void processQueue() {
        if (!isProcessing && !mineQueue.isEmpty()) {
            isProcessing = true;
            Runnable task = mineQueue.poll();
            if (task != null) {
                task.run();
            }
            isProcessing = false;
        }
    }
}