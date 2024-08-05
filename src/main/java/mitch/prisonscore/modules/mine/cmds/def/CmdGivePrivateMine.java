package mitch.prisonscore.modules.mine.cmds.def;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.mine.MineModule;
import mitch.prisonscore.modules.mine.cmds.MineCommands;
import org.bukkit.entity.Player;

public class CmdGivePrivateMine extends MineCommands {

    private static final CmdGivePrivateMine i = new CmdGivePrivateMine();
    public static CmdGivePrivateMine get() { return i; }

    public CmdGivePrivateMine() {
        this.addAliases("giveprivatemine");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        MineModule.get().givePrivateMine(player);

    }
}
