package mitch.prisonscore.modules.mine.utils;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.Getter;
import mitch.prisonscore.modules.publicmines.object.Mine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
public class SerLoc {
    private int x;
    private int y;
    private int z;

    public SerLoc(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SerLoc(BlockVector3 vector) {
        this.x = vector.getBlockX();
        this.y = vector.getBlockY();
        this.z = vector.getBlockZ();
    }

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(x, y, z);
    }

    public SerLoc addS(SerLoc vector) {
        return new SerLoc(x + vector.x, y + vector.y, z + vector.z);
    }
    public BlockVector3 add(SerLoc vector) {
        return this.toBlockVector3().add(vector.toBlockVector3());
    }

    public void add(int amount){
        this.x += amount;
        this.z += amount;
    }

    public BlockVector3 add(BlockVector3 vector) {
        return this.toBlockVector3().add(vector);
    }

    public BlockVector3 add(int x, int y, int z) {
        return this.toBlockVector3().add(x, y, z);
    }

    public Location toLocation(Mine mine) {
        return new Location(Bukkit.getWorld(mine.getWorldName()), x, y, z);
    }

    public Location toLocation(Player player) {
        return new Location(player.getWorld(), x, y, z);
    }

    public Location toLocation(String world) {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public void subtract(int amount) {
        this.x -= amount;
        this.z -= amount;
    }

}
