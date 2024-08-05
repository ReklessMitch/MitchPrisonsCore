package mitch.prisonscore.modules.publicmines;

import com.google.common.collect.Lists;
import lombok.Getter;
import mitch.prisonscore.MitchPrisonsCore;
import mitch.prisonscore.modules.Module;
import mitch.prisonscore.modules.mine.utils.SerLoc;
import mitch.prisonscore.modules.profile.configs.ProfilePlayer;
import mitch.prisonscore.modules.publicmines.cmds.CmdMines;
import mitch.prisonscore.modules.publicmines.cmds.CmdResetPublicMine;
import mitch.prisonscore.modules.publicmines.object.Mine;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import mitch.prisonscore.modules.type.ModuleType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
public class PublicMinesModule extends Module {

    Map<Integer, Mine> publicMines = Map.of(0, new Mine(
                                                    new SerLoc(0,0,0),
                                                    new SerLoc(0,0,0),
                                                    new SerLoc(0,0,0),
                                                    Lists.newArrayList(Material.STONE),
                                                    MineTier.TIER1, "publicmines"));


    public static PublicMinesModule get() {
        return MitchPrisonsCore.get().getModuleByType(ModuleType.PUBLICMINES);
    }

    public PublicMinesModule() {
        super(true);
    }

    @Override
    public ModuleType getType() {
        return ModuleType.PUBLICMINES;
    }

    @Override
    public void enable() {
        if (!isEnabled()) return;
        MitchPrisonsCore.get().activate(
                CmdMines.class,
                CmdResetPublicMine.class
        );
    }

    @Override
    public void disable() {

    }

    public Mine getPlayerMine(Player player) {
        return publicMines.get(ProfilePlayer.get(player.getUniqueId()).getRank());
    }

    public Mine getMineAtLocation(Location location) {
        for (Mine mine : publicMines.values()) {
            if (mine.isInMine(location)) {
                return mine;
            }
        }
        return null;
    }
}
