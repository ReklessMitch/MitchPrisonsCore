package mitch.prisonscore.modules.crystals.cmds.subcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.crystals.cmds.CrystalCommands;
import mitch.prisonscore.modules.crystals.objects.Crystal;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

public class CmdCrystalGive extends CrystalCommands {

    public CmdCrystalGive() {
        this.addAliases("give");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        assert player != null;
        Crystal crystal = new Crystal();
        player.getInventory().addItem(crystal.getCrystalItem());
        MessageUtils.sendMessage(player, "<green>You have been given a crystal.");
    }
}
