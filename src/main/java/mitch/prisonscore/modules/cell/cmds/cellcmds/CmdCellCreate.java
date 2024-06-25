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

public class CmdCellCreate extends CellCommands {

    public CmdCellCreate(){
        this.addAliases("create");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellModule conf = CellModule.get();
        LangConf lang = LangConf.get();
        if(conf.getCellNames().contains(cellName.toUpperCase())){
            MessageUtils.sendMessage(me, lang.getCellNameTaken());
            return;
        }
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getAlreadyInCell());
            return;
        }
        conf.getCells().put(cellName.toUpperCase(), new Cell(cellName, me.getUniqueId()));
        final TagResolver cellNameResolver = Placeholder.parsed("cellname", cellName);
        MessageUtils.sendMessage(me, lang.getCellCreated(), cellNameResolver);
        conf.changed();
    }
}
