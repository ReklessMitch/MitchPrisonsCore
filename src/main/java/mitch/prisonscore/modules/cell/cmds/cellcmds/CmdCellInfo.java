package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.IdUtil;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdCellInfo extends CellCommands {

    public CmdCellInfo(){
        this.addAliases("info");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        CellModule conf = CellModule.get();
        Cell cell = conf.getCellByMember(player.getUniqueId());
        if(cell == null){
            msg("<b>You are not in a cell");
            return;
        }
        msg("<grey>------------<reset> <gold><bold>Cell Info <grey>------------" +
                "\n<grey>Cell Name: <gold>" + cell.getName() +
                "\n<grey>Cell Owner: <gold>" + IdUtil.getOfflinePlayer(cell.getOwner()).getName() +
                "\n<grey>Cell Members: <gold>" + getAllMembers(cell) +
                "\n<grey>Beacons: <gold>" + cell.getBeacons() +
                "\n<grey>----------------------------------");
    }

    // turn Set<List> of cell.getAllMembers() into a string with commas
    public String getAllMembers(Cell cell) {
        StringBuilder allMembers = new StringBuilder();
        for(UUID playerID : cell.getAllMembers()){
            if(playerID.equals(cell.getOwner())) continue; // skip owner (already displayed)
            OfflinePlayer player = IdUtil.getOfflinePlayer(playerID);
            allMembers.append(player.isOnline() ? "<green>" + player.getName() + " " : "<grey>" + player.getName() + " ");
        }
        return allMembers.toString();
    }

}
