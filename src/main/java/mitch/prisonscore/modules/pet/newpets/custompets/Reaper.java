package mitch.prisonscore.modules.pet.newpets.custompets;

import mitch.prisonscore.modules.mine.utils.BlockInPmineBrokeEvent;
import mitch.prisonscore.modules.pet.newpets.Pet;
import mitch.prisonscore.modules.pet.newpets.PetConfig;
import mitch.prisonscore.modules.pet.util.PetType;
import mitch.prisonscore.modules.pickaxe.utils.DisplayItem;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class Reaper extends Pet<Reaper.Config> {

    public Reaper() {
        super(PetType.REAPER, Reaper.Config.class);
    }

    @Override
    public void activate(BlockInPmineBrokeEvent event, int level) {
        int levelsToGoDown = getConfig().baseLevelsToGoDown + (level / getConfig().levelsToGoDownPerXLevels);
        Block block = event.getBlock();
        event.getPlayerMine().getBlocksOnXAxis(block.getX(), block.getY(), levelsToGoDown);
        sendPetMessage(event.getPlayer());
    }

    public static class Config extends PetConfig {
        int baseLevelsToGoDown = 3;
        int levelsToGoDownPerXLevels = 25;
        public Config() {
            super(new DisplayItem(Material.DIAMOND, "base", List.of("test"), 0, 0),
                    100, 0.1, 0.01, "petActivateMessage");
        }
    }
}
