package mitch.prisonscore.modules.cell.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import mitch.prisonscore.modules.cell.cmds.CellCommands;
import mitch.prisonscore.modules.cell.CellModule;
import mitch.prisonscore.modules.cell.object.Cell;
import mitch.prisonscore.utils.LangConf;
import mitch.prisonscore.utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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
        LangConf lang = LangConf.get();
        if(cell == null){
            MessageUtils.sendMessage(me, lang.getNotInACell());
            return;
        }
        if(!cell.getAllHigherUps().contains(me.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getNotHighEnoughCellRank());
            return;
        }
        Player player = this.readArg();
        if(cell.getInvites().contains(player.getUniqueId())){
            MessageUtils.sendMessage(me, lang.getPlayerAlreadyInvitedToCell());
            return;
        }
        cell.getInvites().add(player.getUniqueId());
        final TagResolver playerNameResolver = Placeholder.parsed("player", player.getName());
        final TagResolver cellNameResolver = Placeholder.parsed("cellname", cell.getName());
        final TagResolver inviterNameResolver = Placeholder.parsed("inviter", me.getName());
        MessageUtils.sendMessage(me, lang.getCellInviteSent(), playerNameResolver);
        MessageUtils.sendMessage(player, lang.getCellInviteReceived(), cellNameResolver, inviterNameResolver);
        conf.changed();
    }

}
