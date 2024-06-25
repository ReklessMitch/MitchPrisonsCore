package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class CmdCellJoin extends CellCommands {


    public CmdCellJoin(){
        this.addAliases("join");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        cellName = cellName.toLowerCase();
        CellModule conf = CellModule.get();
        LangConf lang = LangConf.get();
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getAlreadyInCell());
            return;
        }
        Cell cell = conf.getCellByName(cellName);
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getCellDoesNotExist());
            return;
        }
        if(cell.getMembers().size() >= conf.getMaxCellSize()){

            MessageUtils.sendMessage(me, lang.getCellIsFull());
            return;
        }
        if(cell.getInvites().contains(me.getUniqueId())){
            cell.getMembers().add(me.getUniqueId());
            final TagResolver cellNameResolver = Placeholder.parsed("cellname", cellName);
            MessageUtils.sendMessage(me, lang.getCellJoinedSuccess(), cellNameResolver);
            cell.getInvites().remove(me.getUniqueId());
            conf.changed();
        }else{
            MessageUtils.sendMessage(me, lang.getNotInvitedToCell());
        }


    }


}
