package mitch.prisonscore.modules.pickaxe.cmds.pickaxe;

import com.massivecraft.massivecore.Lang;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.pickaxe.cmds.PickaxeCommands;
import mitch.prisonscore.modules.pickaxe.configs.PickaxePlayer;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class CmdBlocks extends PickaxeCommands {

    private static final CmdBlocks i = new CmdBlocks();
    public static CmdBlocks get() { return i; }

    public CmdBlocks() {
        this.addAliases("blocks");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        if(player == null) player = me.getPlayer();
        assert player != null;
        PickaxePlayer pick = PickaxePlayer.get(player.getUniqueId());
        final TagResolver playerResolver = Placeholder.parsed("player", player.getName());
        final TagResolver blocksResolver = Placeholder.parsed("blocks", String.valueOf(pick.getBlocksBroken()));
        final TagResolver rawBlocksResolver = Placeholder.parsed("rawblocks", String.valueOf(pick.getRawBlocksBroken()));
        MessageUtils.sendMessages(sender, LangConf.get().getBlocksFormat(), playerResolver, blocksResolver, rawBlocksResolver);
    }
}
