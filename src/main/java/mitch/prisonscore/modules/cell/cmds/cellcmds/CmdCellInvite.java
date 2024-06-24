package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.MessageUtils;
import org.bukkit.entity.Player;

public class CmdCellInvite extends CellCommands {

    public CmdCellInvite(){
        this.addAliases("invite");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        CellModule conf = CellModule.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());

        if(cell == null){
            MessageUtils.sendMessage(me, "<red>You are not in a cell");
            return;
        }
        if(!cell.getAllHigherUps().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>You are not a higher up in this cell");
            return;
        }
        Player player = this.readArg();
        if(cell.getInvites().contains(player.getUniqueId())){
            MessageUtils.sendMessage(me, "<red>Player is already invited");
            return;
        }
        cell.getInvites().add(player.getUniqueId());
        MessageUtils.sendMessage(me, "<green>Invited " + player.getName() + " to your cell");
        MessageUtils.sendMessage(player, "<green>You have been invited to " + cell.getName() + "'s cell");
        conf.changed();
    }

}
