package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.IdUtil;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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
        LangConf lang = LangConf.get();
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getNotInACell());
            return;
        }
        final String cellOwnerName = IdUtil.getOfflinePlayer(cell.getOwner()).getName();
        final TagResolver cellName = Placeholder.parsed("cellname", cell.getName());
        final TagResolver cellOwner = Placeholder.parsed("cellowner", cellOwnerName + "");
        final TagResolver cellMembers = Placeholder.parsed("cellmembers", getAllMembers(cell));
        final TagResolver cellBeacons = Placeholder.parsed("cellbeacons", cell.getBeacons().toString());

        MessageUtils.sendMessages(me, lang.getCellInfo(), cellName, cellOwner, cellMembers, cellBeacons);
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
