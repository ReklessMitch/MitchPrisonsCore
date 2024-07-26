package mitch.prisonscore.modules.publicmines.cmds;

import com.massivecraft.massivecore.MassiveException;
import mitch.prisonscore.modules.mine.configs.MinePlayer;
import mitch.prisonscore.modules.publicmines.utils.MineTier;
import mitch.prisonscore.modules.publicmines.utils.TypeMineTier;

public class CmdSetMineSize extends PublicMineCommands{

    private static final CmdSetMineSize i = new CmdSetMineSize();
    public static CmdSetMineSize get() { return i; }

    public CmdSetMineSize() {
        this.addAliases("setminesize");
        this.addParameter(TypeMineTier.get(), "tier");
    }

    @Override
    public void perform() throws MassiveException {
        MineTier tier = this.readArg();
        MinePlayer minePlayer = MinePlayer.get(me.getUniqueId());
        MineTier currentTier = minePlayer.getMine().getTier();
        if(currentTier == MineTier.NOT_A_TIER) {
            minePlayer.getMine().setTier(tier);
            minePlayer.changed();
            minePlayer.generateSchematic();
        }else{
            minePlayer.increaseSize(tier);
        }
    }
}
